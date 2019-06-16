package org.uadaf.app.ith

interface ITHStoryTitleView {

    fun setTitle(id: String, title: String)

}

interface ITHStoryTagsView {

    fun addTag(tag: String)

    fun clearTags()

}

interface ITHStoryTextView {

    fun setText(text: String)

}

interface ITHStoryInfoView {

    fun setDate(date: String)

}