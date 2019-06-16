package org.uadaf.app.quoter

interface QuoteRowView {

    fun setID(id: Int)

    fun setAdder(adder: String)

    fun setAuthor(author: String)

    fun setText(text: String)

    fun setEdited(by: String, at: String)

    fun setNotEdited()

}