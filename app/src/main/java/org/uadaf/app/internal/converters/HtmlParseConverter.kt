package org.uadaf.app.internal.converters

import android.text.Html
import org.jsoup.nodes.Element
import pl.droidsonroids.jspoon.ElementConverter
import pl.droidsonroids.jspoon.annotation.Selector


class HtmlParseConverter : ElementConverter<String> {

    override fun convert(node: Element, selector: Selector): String {
        return Html.fromHtml(node.html()).toString().trim()
    }

}