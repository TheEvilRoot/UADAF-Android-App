package org.uadaf.app.quoter

import org.uadaf.app.quoter.sorting.Sorting
import quoter.DisplayType
import quoter.Quote

interface QuoterRepository {

    fun getQuotesCount(): Int

    fun getQuote(pos: Int): Quote

    fun fetchQuotes(repoName: String)

    fun clearRepo()

    fun checkRepo(repoName: String): Boolean

    fun sectionNameBySorting(quotePosition: Int): String

    fun setSortingAlgorithm(sortingIn: Sorting)

    fun setSortingDirection(directionIn: Sorting.Direction)

    fun sort()

    fun addQuote(adder: String, author: String, content: String, displayType: DisplayType, attachments: List<String>, repo: String)

}