package org.uadaf.app.ith.list

import org.uadaf.app.internal.view.IErrorView

interface ITHStoriesListView: IErrorView {

    fun updateStories(added: Boolean = false)

    fun displayNoLiked()

    fun displayNoStories()

    fun displayLoading()

    fun hideLoading()

}