package org.uadaf.app.quoter

import org.uadaf.app.internal.view.IErrorView

interface QuoterView: IErrorView {

    fun updateQuotesView()

    fun displayNoQuotes()

    fun displayNoInternet()

    fun displayServiceUnavailable()

    fun displayLoading()

    fun stopLoading()

}