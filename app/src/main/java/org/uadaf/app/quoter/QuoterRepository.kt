package org.uadaf.app.quoter

import quoter.Quote

interface QuoterRepository {

    fun getQuotesCount(): Int

    fun getQuote(pos: Int): Quote

    fun fetchQuotes(repoName: String)

    fun clearRepo()

    fun checkRepo(repoName: String): Boolean

}