package org.uadaf.app.quoter.impl

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.future.future
import kotlinx.coroutines.runBlocking
import org.uadaf.app.internal.UADAFServiceException
import org.uadaf.app.quoter.QuoterAPI
import org.uadaf.app.quoter.QuoterRepository
import org.uadaf.app.quoter.sorting.Sorting
import org.uadaf.app.quoter.sorting.Sorting.Direction
import quoter.DisplayType
import quoter.Quote

class QuoterRepositoryImpl(
    private val quoterApi: QuoterAPI
) : QuoterRepository {

    private var sorting: Sorting = Sorting.Id
    private var direction = Direction.Ascending

    private val quotesList = ArrayList<Quote>()

    override fun getQuotesCount():   Int =
        quotesList.count()

    override fun getQuote(pos: Int): Quote =
        quotesList[pos]

    override fun fetchQuotes(repoName: String) {
        val quotesCall = quoterApi.quoter().all(repoName).execute()

        if (quotesCall.isSuccessful) {
            val payload = quotesCall.body()
            if (payload != null) {
                clearRepo()
                quotesList.addAll(payload)
                sort()
            } else {
                throw UADAFServiceException("Quoter", "/all?resolver=$repoName payload is null")
            }
        } else {
            throw UADAFServiceException("Quoter", "${quotesCall.raw().request().url()} status code ${quotesCall.code()}")
        }
    }

    override fun checkRepo(repoName: String): Boolean {
        quoterApi.quoter().total(repoName).execute()
        return true
    }

    override fun clearRepo() {
        quotesList.clear()
    }

    private fun updateSorting() {
        quotesList.sortWith(sorting.comparator)
    }

    private fun updateOrder() {
        if (direction == Direction.Descending) {
            quotesList.reverse()
        }
    }

    override fun setSortingAlgorithm(sortingIn: Sorting) {
        sorting = sortingIn
        updateSorting()
    }

    override fun setSortingDirection(directionIn: Direction) {
        direction = directionIn
        updateOrder()
    }

    override fun sort() {
        updateSorting()
        updateOrder()
    }

    override fun sectionNameBySorting(quotePosition: Int): String =
        sorting.section(getQuote(quotePosition))

    override fun addQuote(
        adder: String,
        author: String,
        content: String,
        displayType: DisplayType,
        attachments: List<String>,
        repo: String
    ) {
       // val result = runBlocking { quoterApi.quoter().add(adder, author, content, displayType, attachments, repo) }
    }

}