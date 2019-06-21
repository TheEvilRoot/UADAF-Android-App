package org.uadaf.app.ith.story

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_ithstory.*
import me.everything.android.ui.overscroll.IOverScrollState
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance
import org.uadaf.app.R
import org.uadaf.app.internal.exceptions.ExceptionDispatcher
import org.uadaf.app.ith.ITHRepository
import org.uadaf.app.ith.ITHStoryAdapter
import org.uadaf.app.ith.story.impl.ITHStoryPresenterImpl
import org.uadaf.app.main.MainView

class ITHStoryFragment : Fragment(), KodeinAware, ITHStoryView {

    override val kodein: Kodein by kodein()
    private val repository: ITHRepository by instance()
    private val exceptionDispatcher: ExceptionDispatcher by instance()
    private val mainView: MainView by instance()
    private val presenter by lazy { ITHStoryPresenterImpl(repository, this@ITHStoryFragment, exceptionDispatcher) }
    private val adapter by lazy { ITHStoryAdapter(presenter) }

    private var currentLiked = false


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_ithstory, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.run {
            toolbar.setupWithNavController(findNavController())
            recyclerView.layoutManager = LinearLayoutManager(context)
            recyclerView.adapter = adapter
            val decor =
                OverScrollDecoratorHelper.setUpOverScroll(recyclerView, OverScrollDecoratorHelper.ORIENTATION_VERTICAL)
            recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (dy > 0 && (fabLike.visibility == View.VISIBLE || fabUnlike.visibility == View.VISIBLE)) {
                        fabLike.hide()
                        fabUnlike.hide()
                        fabJump.hide()
                        fabWeb.hide()
                    } else if (dy < 0 && (fabLike.visibility != View.VISIBLE && fabUnlike.visibility != View.VISIBLE)) {
                        setLiked(currentLiked)
                        fabWeb.show()
                        fabJump.show()
                    }
                }
            })
            decor.setOverScrollStateListener { _, _, newState ->
                when (newState) {
                    IOverScrollState.STATE_DRAG_START_SIDE -> {
                        if (fabLike.visibility != View.VISIBLE && fabUnlike.visibility != View.VISIBLE) {
                            setLiked(currentLiked)
                            fabWeb.show()
                            fabJump.show()
                        }
                    }

                    IOverScrollState.STATE_DRAG_END_SIDE -> {
                        if (fabLike.visibility == View.VISIBLE || fabUnlike.visibility == View.VISIBLE) {
                            fabLike.hide()
                            fabUnlike.hide()
                            fabJump.hide()
                            fabWeb.hide()
                        }
                    }
                }

            }
            fabLike.setOnClickListener {
                presenter.like()
            }
            fabUnlike.setOnClickListener {
                presenter.unlike()
            }
            fabWeb.setOnClickListener {
                presenter.openInWeb(context)
            }
            fabJump.setOnClickListener {
                presenter.jump()
            }
        }
        setLiked(false)
        presenter.load(arguments)
    }

    override fun displayInvalidData() {
        Toast.makeText(context, "Invalid intent data", Toast.LENGTH_SHORT).show()
        findNavController().navigateUp()
    }

    override fun displayStoryNotFound() {
        Toast.makeText(context, "Story not found", Toast.LENGTH_SHORT).show()
        findNavController().navigateUp()
    }

    override fun displayStory(storyId: String) {
        adapter.notifyDataSetChanged()
        view?.run {
            toolbar.title = "#$storyId"
        }
    }

    override fun showLoading() {
        mainView.displayLoading()
    }

    override fun hideLoading() {
        mainView.stopLoading()
    }

    override fun displayError(message: String) {
        Toast.makeText(context, "Error: $message", Toast.LENGTH_SHORT).show()
        findNavController().navigateUp()
    }

    override fun setLiked(liked: Boolean) {
        currentLiked = liked
        view?.run {
            fabLike.isEnabled = true
            fabUnlike.isEnabled = true
            if (liked) {
                fabLike.hide()
                fabUnlike.show()
            } else {
                fabLike.show()
                fabUnlike.hide()
            }
        }
    }

    override fun disableLike() {
        view?.run {
            fabLike.isEnabled = false
            fabUnlike.isEnabled = false
        }
    }

}
