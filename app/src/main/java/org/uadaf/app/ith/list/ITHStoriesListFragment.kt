package org.uadaf.app.ith.list


import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.fragment_ith_stories_list.view.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance
import org.uadaf.app.R
import org.uadaf.app.UADAF
import org.uadaf.app.internal.exceptions.ExceptionDispatcher
import org.uadaf.app.ith.ITHRepository
import org.uadaf.app.ith.list.impl.ITHStoriesListItemAdapter
import org.uadaf.app.ith.list.impl.ITHStoriesListPresenterImpl
import org.uadaf.app.main.MainView

class ITHStoriesListFragment : Fragment(),ITHStoriesListView ,KodeinAware {

    override val kodein: Kodein by kodein(this@ITHStoriesListFragment.requireContext())

    private val ithRepository: ITHRepository by instance()
    private val exceptionDispatcher: ExceptionDispatcher by instance()
    private val mainView: MainView by instance()

    private val presenter: ITHStoriesListPresenter by lazy { ITHStoriesListPresenterImpl(this, ithRepository, exceptionDispatcher) }
    private val adapter: ITHStoriesListItemAdapter by lazy { ITHStoriesListItemAdapter(presenter) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_ith_stories_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val onlyLiked = arguments?.getBoolean("onlyLiked") ?: false
        presenter.setMode(onlyLiked)

        view.run {
            toolbar.setupWithNavController(findNavController())

            if (onlyLiked) {
                toolbar.title = context.getString(R.string.liked_stories_title)
            } else {
                toolbar.title = context.getString(R.string.all_stories_title)
            }

            setupRecyclerView()
        }

        presenter.get()
    }

    private fun View.setupRecyclerView() {
        recyclerView.layoutManager = GridLayoutManager(context, 2)
        recyclerView.adapter = adapter
    }

    override fun displayNoLiked() {
        Toast.makeText(context, "No liked", Toast.LENGTH_SHORT).show()
    }

    override fun displayNoStories() {
        Toast.makeText(context, "No stories", Toast.LENGTH_SHORT).show()

    }

    override fun updateStories(added: Boolean) {
        if (added) {
            adapter.notifyItemInserted(presenter.storiesCount() - 1)
        } else{
            adapter.notifyDataSetChanged()
        }
    }

    override fun displayError(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun displayLoading() {
        mainView.displayLoading()
    }

    override fun hideLoading() {
        mainView.stopLoading()
    }

}
