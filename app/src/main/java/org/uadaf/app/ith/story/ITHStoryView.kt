package org.uadaf.app.ith.story

import org.uadaf.app.internal.view.IErrorView

interface ITHStoryView: IErrorView {

    fun displayInvalidData()

    fun displayStoryNotFound()

    fun displayStory(storyId: String)

    fun showLoading()

    fun hideLoading()

    fun disableLike()

    fun setLiked(liked: Boolean)

}