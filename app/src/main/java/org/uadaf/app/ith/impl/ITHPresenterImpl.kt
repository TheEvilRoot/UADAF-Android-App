package org.uadaf.app.ith.impl

import android.graphics.Bitmap
import com.squareup.picasso.Picasso
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.uadaf.app.internal.ITHStoryDoesNotExists
import org.uadaf.app.internal.exceptions.ExceptionDispatcher
import org.uadaf.app.ith.*
import org.uadaf.app.ith.data.ITHStory
import org.uadaf.app.ith.data.ITHUser

class ITHPresenterImpl (
    private val repository: ITHRepository,
    private val view: ITHView,
    private val exceptionDispatcher: ExceptionDispatcher
): ITHPresenter {

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    private var userImage: Bitmap? = null
    private var isErrored: Boolean = false

    override fun preapre() {
        if (repository.user() == null) {
            val savedUsername = repository.getLocalUsername()
                ?: return view.displayUsernameDialog()

            view.startLoading()
            compositeDisposable.add(Single.fromCallable <ITHUser> {
                repository.initUser(savedUsername)
            }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe({
                view.stopLoading()
                view.displayUsername(it.username)
                requestUserImage()
                loadCurrent()
            }) {
                view.stopLoading()
                exceptionDispatcher.dispatch(it, view)
            })
        } else {
            loadCurrent()
            requestUserImage()
            view.displayUsername(repository.user()!!.username)
        }
    }

    override fun loadCurrent() {
        if (repository.getCurrentID() <= 1) {
            view.setPreviousState(false)
        } else {
            view.setPreviousState(true)
        }

        if (repository.getCurrentStory() != null && repository.getCurrentID() == repository.getCurrentStory()?.id?.toIntOrNull()) {
            view.displayStory()
            requestStoryLikedState()
        } else {
            view.startLoading()
            compositeDisposable.add(Single.fromCallable<ITHStory> {
                repository.fetchStory(repository.getCurrentID())
            }.observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe({
                view.stopLoading()
                repository.setCurrentStory(it)
                view.displayStory()
                requestStoryLikedState()
                isErrored = false
            }) {
                view.stopLoading()
                if (it is ITHStoryDoesNotExists) {
                    view.displayStoryDoesNotExists(it.storyID)
                } else {
                    exceptionDispatcher.dispatch(it, view)
                    isErrored = true
                }
            })

        }

    }

    override fun loadNext() {
        compositeDisposable.add(Single.fromCallable<Int> {
            if (!isErrored){
                repository.increaseCurrent()
            }
            return@fromCallable repository.getCurrentID()
        }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe({
            loadCurrent()
        }) {
            exceptionDispatcher.dispatch(it, view)
        })
    }

    override fun loadPrevious() {
        compositeDisposable.add(Single.fromCallable<Int> {
            if (!isErrored) {
                repository.decreaseCurrent()
            }
            return@fromCallable repository.getCurrentID()
        }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe({
            loadCurrent()
        }) {
            exceptionDispatcher.dispatch(it, view)
        })
    }

    override fun getCurrentStory(): ITHStory? =
        repository.getCurrentStory()

    override fun getCurrentStoryID(): Int =
        repository.getCurrentID()

    override fun like() {
        compositeDisposable.add(Single.fromCallable<Boolean> {
            repository.like(getCurrentStoryID())
            return@fromCallable repository.isStoryLiked(getCurrentStoryID())
        }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(view::setLiked) {
            exceptionDispatcher.dispatch(it, view)
        })
    }

    override fun unlike() {
        compositeDisposable.add(Single.fromCallable<Boolean> {
            repository.unlike(getCurrentStoryID())
            return@fromCallable repository.isStoryLiked(getCurrentStoryID())
        }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(view::setLiked) {
            exceptionDispatcher.dispatch(it, view)
        })
    }

    override fun bindStoryTitle(rowView: ITHStoryTitleView) {
        if (repository.getCurrentStory() == null) {
            loadCurrent()
        }
        repository.getCurrentStory()?.run {
            rowView.setTitle(id, title)
        }
    }
    override fun bindStoryTags(rowView: ITHStoryTagsView) {
        if (repository.getCurrentStory() == null) {
            loadCurrent()
        }
        repository.getCurrentStory()?.run {
            rowView.clearTags()
            tags.forEach {
                rowView.addTag(it)
            }
        }
    }
    override fun bindStoryText(rowView: ITHStoryTextView) {
        if (repository.getCurrentStory() == null) {
            loadCurrent()
        }
        repository.getCurrentStory()?.run {
            rowView.setText(text)
        }
    }
    override fun bindStoryInfo(rowView: ITHStoryInfoView) {
        if (repository.getCurrentStory() == null) {
            loadCurrent()
        }
        repository.getCurrentStory()?.run {
            rowView.setDate(date)
        }
    }

    override fun requestStoryLikedState() {
        view.disableLike()
        compositeDisposable.add(Single.fromCallable<Boolean> {
            repository.isStoryLiked(repository.getCurrentID())
        }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe({
            view.setLiked(it)
        }) {
            view.disableLike()
        })
    }

    override fun requestUserImage() {
        // TODO backend
        compositeDisposable.add(Single.fromCallable <Bitmap> {
            userImage ?: Picasso.get().load("http://52.48.142.75/images/gear.png").get()
        }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe({
            userImage = it
            view.displayUserImage(it)
        }) {
            view.displayNoUserImage()
            exceptionDispatcher.dispatch(it, view)
        })
    }

    override fun getRowsCount(): Int =
        if (getCurrentStory() == null) 0 else 4
}