package org.uadaf.app.main

interface MainView {

    fun displayLoading()

    fun stopLoading()

    fun updateNavigationBadges()

    fun displayFatalError(message: String, throwable: Throwable)

}