package org.uadaf.app.quoter

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.afollestad.materialdialogs.LayoutMode
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.WhichButton
import com.afollestad.materialdialogs.actions.setActionButtonEnabled
import com.afollestad.materialdialogs.bottomsheets.BottomSheet
import com.afollestad.materialdialogs.bottomsheets.setPeekHeight
import com.afollestad.materialdialogs.input.getInputField
import com.afollestad.materialdialogs.input.input
import kotlinx.android.synthetic.main.fragment_quoter.*
import kotlinx.android.synthetic.main.fragment_quoter.view.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance
import org.uadaf.app.R
import org.uadaf.app.internal.exceptions.ExceptionDispatcher
import org.uadaf.app.internal.view.fabs.FABMode
import org.uadaf.app.main.MainView
import org.uadaf.app.notificationcenter.NotificationCenter
import org.uadaf.app.quoter.impl.QuoterPresenterImpl

class QuoterFragment : Fragment(), KodeinAware, QuoterView {

    override val kodein: Kodein by kodein()

    private val exceptionDispatcher: ExceptionDispatcher by instance()
    private val repository: QuoterRepository by instance()
    private val notificationCenter: NotificationCenter by instance()
    private val mainActivityView: MainView by instance()

    private val presenter: QuoterPresenter by lazy { QuoterPresenterImpl(notificationCenter, repository, this, exceptionDispatcher) }
    private val adapter: QuoterAdapter by lazy { QuoterAdapter(presenter) }

    private val fabMenuMode: FABMode = FABMode(R.string.menu_title, R.drawable.ic_menu) {
        changeMenu(fabCloseMode)
    }

    private val fabCloseMode: FABMode = FABMode(R.string.menu_close_title, R.drawable.ic_close) {
        changeMenu(fabMenuMode)
    }

    private val fabReloadMode: FABMode = FABMode(R.string.reload_title, R.drawable.ic_reload) {
        presenter.loadQuotes()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_quoter, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(view) {
            quoterRecyclerView.layoutManager = LinearLayoutManager(context)
            quoterRecyclerView.adapter = adapter

            quoterRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (dy > 0 && fabMenu.visibility == View.VISIBLE) {
                        fabMenu.hide()
                        if (fabMenu.currentMode != fabMenuMode) {
                            changeMenu(fabMenuMode)
                        }
                    } else if (dy < 0 && fabMenu.visibility != View.VISIBLE) {
                        fabMenu.show()
                    }
                }
            })
            quoterRecyclerView.setFastScrollEnabled(true)
            toolbar.setOnClickListener {
                MaterialDialog(windowContext = context, dialogBehavior = BottomSheet(LayoutMode.WRAP_CONTENT)).show {
                    cornerRadius(16f)
                    title(text = "Quoter repository")
                    input(hint = "Repository name... (uadaf by default)", prefill = presenter.repoName())
                    positiveButton(text = "Submit") {
                        presenter.updateRepo(getInputField().text.toString())
                    }
                }
            }
        }


        changeMenu(fabMenuMode)
        presenter.loadQuotes()
    }

    private fun changeMenu(fabMode: FABMode) {
        if (fabMode == fabCloseMode) {
            fabAdd.show()
            fabSearch.show()
        } else {
            fabAdd.hide()
            fabSearch.hide()
        }

        fabMenu.switchMode(fabMode)
    }

    override fun updateQuotesView() {
        view?.run {
            quoterRecyclerView.visibility = View.VISIBLE
            adapter.notifyDataSetChanged()
            changeMenu(fabMenuMode)
        }
    }

    override fun displayNoQuotes() {
        view?.run {
            quoterRecyclerView.visibility = View.INVISIBLE
            changeMenu(fabMenuMode)
        }
    }

    override fun displayNoInternet() {
        view?.run {
            quoterRecyclerView.visibility = View.INVISIBLE
            changeMenu(fabReloadMode)
        }
    }

    override fun displayServiceUnavailable() {
        view?.run {
            quoterRecyclerView.visibility = View.INVISIBLE
            changeMenu(fabReloadMode)
        }
    }

    override fun displayError(message: String) {
        view?.run {
            quoterRecyclerView.visibility = View.INVISIBLE
            changeMenu(fabReloadMode)
        }
        Toast.makeText(context, "Error: $message", Toast.LENGTH_LONG).show()
    }

    override fun displayLoading() {
        mainActivityView.displayLoading()
    }

    override fun stopLoading() {
        mainActivityView.stopLoading()
    }

    override fun repoError(message: String) {
        Toast.makeText(context, "Repo error: $message", Toast.LENGTH_LONG).show()
    }

    override fun updateRepo(newRepo: String) {
        view?.run {
            repoNameView.text = newRepo
        }
    }


}
