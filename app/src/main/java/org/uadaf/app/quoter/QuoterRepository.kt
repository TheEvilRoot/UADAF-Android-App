package org.uadaf.app.quoter

import org.uadaf.app.quoter.data.Quote

interface QuoterRepository {

    fun getQuotesCount(): Int

    fun getQuote(pos: Int): Quote

    fun fetchQuotes()

}