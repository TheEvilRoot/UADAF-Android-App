package org.uadaf.app.ith.data

import org.uadaf.app.internal.converters.HtmlParseConverter
import pl.droidsonroids.jspoon.annotation.Selector

class ITHStory {

    @Selector(".story > .id > span")
    lateinit var id: String

    @Selector(".story > h1")
    lateinit var title: String

    @Selector(".story > .meta > .tags > ul > li")
    var tags: List<String> = emptyList()

    @Selector(".story > .meta > .date-time")
    lateinit var date: String

    @Selector(".story > .text", converter = HtmlParseConverter::class)
    lateinit var text: String
}