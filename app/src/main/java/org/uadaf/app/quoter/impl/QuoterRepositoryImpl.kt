package org.uadaf.app.quoter.impl

import kotlinx.coroutines.runBlocking
import org.uadaf.app.quoter.QuoterAPI
import org.uadaf.app.quoter.QuoterRepository
import org.uadaf.app.quoter.sorting.Sorting
import org.uadaf.app.quoter.sorting.Sorting.Direction
import quoter.Quote

class QuoterRepositoryImpl(
    private val quoterApi: QuoterAPI
): QuoterRepository {

    private var sorting: Sorting = Sorting.Authors
    private var direction = Direction.Ascending

    private val quotesList = ArrayList<Quote>()

    override fun getQuotesCount(): Int =
        quotesList.count()

    override fun getQuote(pos: Int): Quote =
        quotesList[pos]

    override fun fetchQuotes(repoName: String) {
        val quotes = runBlocking { quoterApi.quoter().all(repo = repoName) }
        clearRepo()
        quotesList.addAll(quotes)
        sort()
    }

    override fun checkRepo(repoName: String): Boolean {
        runBlocking { quoterApi.quoter().total(repo = repoName) }
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

}