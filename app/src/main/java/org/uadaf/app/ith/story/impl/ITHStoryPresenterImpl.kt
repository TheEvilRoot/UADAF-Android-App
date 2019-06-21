package org.uadaf.app.ith.story.impl

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.uadaf.app.internal.ITHStoryDoesNotExists
import org.uadaf.app.internal.exceptions.ExceptionDispatcher
import org.uadaf.app.ith.*
import org.uadaf.app.ith.data.ITHStory
import org.uadaf.app.ith.story.ITHStoryPresenter
import org.uadaf.app.ith.story.ITHStoryView


class ITHStoryPresenterImpl(
    val repository: ITHRepository,
    val view: ITHStoryView,
    val exceptionDispatcher: ExceptionDispatcher
) : ITHStoryPresenter {

    private var current: ITHStory? = null

    private val trashBin: CompositeDisposable = CompositeDisposable()

    override fun load(arguments: Bundle?) {
        view.showLoading()
        val storyId = arguments?.get("id")?.toString()?.toIntOrNull()

        if (storyId == null) {
            view.displayInvalidData()
            return
        }

        trashBin.add(Single.fromCallable<ITHStory> {
            repository.get(storyId) ?: repository.fetchStory(storyId)
        }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe({
            current = it
            view.displayStory(it.id)
            view.hideLoading()
            requestStoryLikedState()
        }) {
            if (it is ITHStoryDoesNotExists) {
                view.displayStoryNotFound()
            } else {
                exceptionDispatcher.dispatch(it, view)
            }
            view.displayStoryNotFound()
            view.hideLoading()
        })
    }

    override fun like() {
        if (current != null) {
            val id = current?.id?.toIntOrNull() ?: return
            trashBin.add(Single.fromCallable<Boolean> {
                repository.like(id)
                return@fromCallable repository.isStoryLiked(id)
            }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(view::setLiked) {
                exceptionDispatcher.dispatch(it, view)
            })
        }
    }

    override fun unlike() {
        if (current != null) {
            val id = current?.id?.toIntOrNull() ?: return
            trashBin.add(Single.fromCallable<Boolean> {
                repository.unlike(id)
                return@fromCallable repository.isStoryLiked(id)
            }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(view::setLiked) {
                exceptionDispatcher.dispatch(it, view)
            })
        }
    }

    override fun bindStoryTitle(rowView: ITHStoryTitleView) {
        if (current != null) {
            rowView.setTitle(current!!.id, current!!.title)
        } else {
            rowView.setTitle("", "")
        }
    }

    override fun bindStoryTags(rowView: ITHStoryTagsView) {
        rowView.clearTags()
        current?.tags?.forEach {
            rowView.addTag(it)
        }

    }

    override fun bindStoryText(rowView: ITHStoryTextView) {
        if (current != null) {
            rowView.setText(current!!.text)
        } else {
            rowView.setText("")
        }
    }

    override fun bindStoryInfo(rowView: ITHStoryInfoView) {
        if (current != null) {
            rowView.setDate(current!!.date)
        } else {
            rowView.setDate("")
        }
    }

    override fun requestStoryLikedState() {
        if (current != null) {
            val id = current?.id?.toIntOrNull() ?: return
            view.disableLike()
            trashBin.add(Single.fromCallable<Boolean> {
                repository.isStoryLiked(id)
            }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe({
                view.setLiked(it)
            }) {
                view.disableLike()
            })
        }
    }

    override fun jump() {
        // TODO
    }

    override fun openInWeb(context: Context) {
        val applicationContext = context.applicationContext

        if (current != null) {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://ithappens.me/story/${current!!.id}"))
            val defaultResolution =
                applicationContext.packageManager.resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY)

            if (defaultResolution.activityInfo.packageName == applicationContext.packageName) {
                val browseIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://"))
                val browseResolution =
                    applicationContext.packageManager.resolveActivity(browseIntent, PackageManager.MATCH_DEFAULT_ONLY)

                intent.component = ComponentName(
                    browseResolution.activityInfo.applicationInfo.packageName,
                    browseResolution.activityInfo.name
                )
            }
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            applicationContext.startActivity(intent)
        }
    }

    override fun getRowsCount(): Int =
        if (current == null) 0 else 4

}