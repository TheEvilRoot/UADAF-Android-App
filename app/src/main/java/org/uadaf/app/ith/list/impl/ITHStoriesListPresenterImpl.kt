package org.uadaf.app.ith.list.impl

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.uadaf.app.internal.exceptions.ExceptionDispatcher
import org.uadaf.app.ith.ITHRepository
import org.uadaf.app.ith.data.ITHStory
import org.uadaf.app.ith.list.ITHStoriesListPresenter
import org.uadaf.app.ith.list.ITHStoriesListRowView
import org.uadaf.app.ith.list.ITHStoriesListView

class ITHStoriesListPresenterImpl(
    private val view: ITHStoriesListView,
    private val repository: ITHRepository,
    private val exceptionDispatcher: ExceptionDispatcher
) : ITHStoriesListPresenter {

    private var mode: Boolean = false
    private val trashBin: CompositeDisposable = CompositeDisposable()

    private val dataSet: ArrayList<ITHStory> = ArrayList()

    override fun get() {
        dataSet.clear()
        view.displayLoading()
        trashBin.add(Observable.create<ITHStory> {
            val ids = if (mode) { // Only liked display
                repository.getLiked()
            } else {
                repository.getStories()
            }

            for (id in ids) {
                val story = repository.get(id)
                    ?: repository.fetchStory(id)

                it.onNext(story)
            }

            it.onComplete()
        }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe({
            dataSet.add(it)
            view.updateStories(true)
        }, {
            exceptionDispatcher.dispatch(it, view)
            view.hideLoading()
        }) {
            view.hideLoading()
            view.updateStories()
        })
    }


    override fun bindStoriesListRow(position: Int, rowView: ITHStoriesListRowView) {
        val story = dataSet[position]
        rowView.setId(story.id)
        rowView.setTitle(story.title)
        rowView.setTags(story.tags.joinToString(", "))
    }

    override fun storiesCount(): Int =
        dataSet.count()

    override fun setMode(onlyLiked: Boolean) {
        mode = onlyLiked
    }

}