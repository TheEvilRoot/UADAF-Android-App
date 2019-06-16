package org.uadaf.app.quoter.impl

import kotlinx.coroutines.runBlocking
import org.uadaf.app.quoter.QuoterAPI
import org.uadaf.app.quoter.QuoterRepository
import quoter.Quote

class QuoterRepositoryImpl(
    private val quoterApi: QuoterAPI
): QuoterRepository {

    private val quotesList = ArrayList<Quote>()

    override fun getQuotesCount(): Int =
        quotesList.count()

    override fun getQuote(pos: Int): Quote =
        quotesList[pos]

    override fun fetchQuotes(repoName: String) {
        val quotes = runBlocking { quoterApi.quoter().all(repo = repoName) }
        clearRepo()
        quotesList.addAll(quotes)
    }

    override fun checkRepo(repoName: String): Boolean {
        runBlocking { quoterApi.quoter().total(repo = repoName) }
        return true
    }

    override fun clearRepo() {
        quotesList.clear()
    }

}