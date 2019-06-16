package org.uadaf.app.ith

interface ITHRowViewPresenter {
    fun bindStoryTitle(rowView: ITHStoryTitleView)

    fun bindStoryTags(rowView: ITHStoryTagsView)

    fun bindStoryText(rowView: ITHStoryTextView)

    fun bindStoryInfo(rowView: ITHStoryInfoView)

    fun getRowsCount(): Int
}