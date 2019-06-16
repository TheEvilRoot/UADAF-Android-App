package org.uadaf.app.quoter.impl

import org.uadaf.app.internal.UADAFServiceException
import org.uadaf.app.quoter.QuoterRepository
import org.uadaf.app.quoter.data.Quote
import org.uadaf.app.quoter.service.QuoterService

class QuoterRepositoryImpl(
    private val quoterService: QuoterService
): QuoterRepository {

    private val quotesList = ArrayList<Quote>()

    override fun getQuotesCount(): Int =
        quotesList.count()

    override fun getQuote(pos: Int): Quote =
        quotesList[pos]

    override fun fetchQuotes() {
        val call = quoterService.getAll()
        val response = call.execute()

        if (!response.isSuccessful) {
            throw UADAFServiceException("Quoter", "fetchQuotes")
        }

        val quotes = response.body()
            ?: throw UADAFServiceException("Quoter", "fetchQuotes:emptyBody")

        quotesList.clear()
        quotesList.addAll(quotes)
    }

}