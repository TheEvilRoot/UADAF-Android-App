package org.uadaf.app.quoter

interface QuoterPresenter {

    fun getQuotesCount(): Int

    fun bindQuoteView(position: Int, view: QuoteRowView)

    fun loadQuotes(force: Boolean = false)

    fun updateRepo(repoNameIn: String)

    fun repoName(): String

    fun getSectionName(position: Int): String

}