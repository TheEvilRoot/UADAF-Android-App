package org.uadaf.app.ith.list

interface ITHStoriesListPresenter {

    fun get()

    fun bindStoriesListRow(position: Int, rowView: ITHStoriesListRowView)

    fun setMode(onlyLiked: Boolean)

    fun storiesCount(): Int

}