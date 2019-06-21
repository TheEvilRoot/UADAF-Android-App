package org.uadaf.app.internal.exceptions.impl

import android.content.Context
import android.util.Log
import androidx.annotation.IdRes
import org.uadaf.app.R
import org.uadaf.app.internal.NoConnectivityException
import org.uadaf.app.internal.exceptions.ExceptionDispatcher
import org.uadaf.app.internal.view.IErrorView
import org.uadaf.app.main.MainView
import org.uadaf.app.notificationcenter.NotificationCenter
import org.uadaf.app.notificationcenter.data.Notification
import org.uadaf.app.preferences.PreferencesProvider

class ExceptionDispatcherImpl(
    context: Context,
    private val mainView: MainView,
    private val notificationCenter: NotificationCenter,
    private val preferencesProvider: PreferencesProvider
) : ExceptionDispatcher {

    private val applicationContext: Context = context.applicationContext

    override fun dispatch(exception: Throwable, withView: IErrorView, @IdRes fragment: Int?, message: String) {
        val exceptionMessage = when (exception) {
            is NoConnectivityException -> "Please, check your internet connection"
            else -> message.format(exception.localizedMessage ?: "Unknown error")
        }

        Log.e("ExceptionDispatcher:${withView::class.java.simpleName}", "An exception occurred.", exception)
        withView.displayError(exceptionMessage)


        if (preferencesProvider.boolean(
                preferencesProvider.preferenceName(R.string.preference_exceptions_notification),
                true
            )
        ) {
            notificationCenter.postNotification(Notification(exceptionMessage, fragment))
        }
    }

    override fun dispatchFatal(exception: Throwable) {

    }

}