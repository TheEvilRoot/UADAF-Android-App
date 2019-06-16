package org.uadaf.app.ith

import android.graphics.Bitmap
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_ith.*
import me.everything.android.ui.overscroll.IOverScrollState
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance
import org.uadaf.app.R
import org.uadaf.app.internal.exceptions.ExceptionDispatcher
import org.uadaf.app.ith.impl.ITHPresenterImpl
import org.uadaf.app.main.MainView


class ITHFragment : Fragment(), Toolbar.OnMenuItemClickListener, ITHView, KodeinAware {
    override val kodein: Kodein by kodein()

    private val repository: ITHRepository by instance()
    private val exceptionDispatcher: ExceptionDispatcher by instance()
    private val presenter by lazy { ITHPresenterImpl(repository, this@ITHFragment, exceptionDispatcher) }
    private val adapter by lazy { ITHStoryAdapter(presenter) }

    private val mainView: MainView by instance()

    private var currentLiked = false
    private var currentPrev = true
    private var currentNext = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_ith, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.run {
            toolbar.inflateMenu(R.menu.ith_menu)
            toolbar.setOnMenuItemClickListener(this@ITHFragment)
            ithRecyclerView.layoutManager = LinearLayoutManager(this.context)
            ithRecyclerView.adapter = adapter
            fabLike.setOnClickListener {
                presenter.like()
            }
            fabUnlike.setOnClickListener {
                presenter.unlike()
            }
            fabPrev.setOnClickListener {
                presenter.loadPrevious()
            }
            fabNext.setOnClickListener {
                presenter.loadNext()
            }
            val decor = OverScrollDecoratorHelper.setUpOverScroll(ithRecyclerView, OverScrollDecoratorHelper.ORIENTATION_VERTICAL)
            ithRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (dy > 0 && (fabLike.visibility == View.VISIBLE || fabUnlike.visibility == View.VISIBLE)) {
                        fabLike.hide()
                        fabUnlike.hide()
                        fabPrev.hide()
                        fabNext.hide()
                    } else if (dy < 0 && (fabLike.visibility != View.VISIBLE && fabUnlike.visibility != View.VISIBLE)) {
                        setLiked(currentLiked)
                        setPreviousState(currentPrev)
                        setNextState(currentNext)
                    }
                }
            })
            decor.setOverScrollStateListener { _, _, newState ->
                when (newState) {
                    IOverScrollState.STATE_DRAG_START_SIDE -> {
                        if (fabLike.visibility != View.VISIBLE && fabUnlike.visibility != View.VISIBLE) {
                            setLiked(currentLiked)
                            setPreviousState(currentPrev)
                            setNextState(currentNext)
                        }
                    }

                    IOverScrollState.STATE_DRAG_END_SIDE -> {
                        if (fabLike.visibility == View.VISIBLE || fabUnlike.visibility == View.VISIBLE) {
                            fabLike.hide()
                            fabUnlike.hide()
                            fabPrev.hide()
                            fabNext.hide()
                        }
                    }
                }

            }
            setLiked(false)
        }
        presenter.preapre()
    }

    override fun onMenuItemClick(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.ithAll, R.id.ithFavs -> {
                findNavController().navigate(R.id.ith_stories_list_fragment, Bundle().apply {
                    putBoolean("onlyLiked", item.itemId == R.id.ithFavs)
                })
            }


        }
        return true
    }

    override fun displayStory() {
        view?.run {
            adapter.notifyDataSetChanged()
            ithRecyclerView.visibility = View.VISIBLE
            errorGroup.visibility = View.GONE
        }
    }

    override fun startLoading() {
        view?.run {
            mainView.displayLoading()
            errorGroup.visibility = View.GONE
        }
    }

    override fun stopLoading() {
        mainView.stopLoading()
    }

    override fun displayStoryDoesNotExists(storyID: Int) {
        view?.run {
            fabLike.hide()
            fabUnlike.hide()
            errorGroup.visibility = View.VISIBLE
            ithRecyclerView.visibility = View.INVISIBLE
            errorLabel.text = context.getString(R.string.story_does_not_exists_label, storyID)
            errorImage.setImageDrawable(context.getDrawable(R.drawable.ic_sentiment_dissatisfied))
        }
    }

    override fun displayError(message: String) {
        view?.run {
            fabLike.hide()
            fabUnlike.hide()
            errorGroup.visibility = View.VISIBLE
            ithRecyclerView.visibility = View.INVISIBLE
            errorLabel.text = message
            errorImage.setImageDrawable(context.getDrawable(R.drawable.ic_error_outline))
        }
    }

    override fun setLiked(isLiked: Boolean) {
        currentLiked = isLiked
        view?.run {
            fabLike.isEnabled = true
            fabUnlike.isEnabled = true
            if (isLiked) {
                fabLike.hide()
                fabUnlike.show()
            } else {
                fabLike.show()
                fabUnlike.hide()
            }
        }
    }

    override fun setNextState(enabled: Boolean) {
        currentNext = enabled
        view?.run {
            if (enabled) {
                fabNext.show()
            } else {
                fabNext.hide()
            }
        }
    }

    override fun setPreviousState(enabled: Boolean) {
        currentPrev = enabled
        view?.run {
            if (enabled) {
                fabPrev.show()
            } else {
                fabPrev.hide()
            }
        }
    }

    override fun displayUsernameDialog() {
        view?.run {
            findNavController().navigate(R.id.preferencesFragment)
            Toast.makeText(context, context.getString(R.string.ith_username_set_tip), Toast.LENGTH_LONG).show()
        }
    }

    override fun displayUsername(username: String) {
        view?.run {
            userName.text = username
        }
    }

    override fun displayUserImage(image: Bitmap) {
        view?.run {
            userImage.visibility = View.VISIBLE
            userImage.setImageBitmap(image)
        }
    }

    override fun displayNoUserImage() {
        view?.run {
            userImage.visibility = View.GONE
        }
    }

    override fun disableLike() {
        view?.run {
            fabLike.isEnabled = false
            fabUnlike.isEnabled = false
        }
    }

}
