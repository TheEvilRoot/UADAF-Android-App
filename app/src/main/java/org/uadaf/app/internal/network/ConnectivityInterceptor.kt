package org.uadaf.app.internal.network

import android.content.Context
import android.net.ConnectivityManager
import androidx.preference.PreferenceManager
import okhttp3.Interceptor
import okhttp3.Response
import org.uadaf.app.R
import org.uadaf.app.internal.NoConnectivityException
import org.uadaf.app.notificationcenter.NotificationCenter
import org.uadaf.app.notificationcenter.data.Notification
import org.uadaf.app.preferences.PreferencesProvider
import java.lang.RuntimeException

class ConnectivityInterceptor(
    context: Context,
    private val notificationCenter: NotificationCenter,
    private val preferencesProvider: PreferencesProvider
): Interceptor {

    private val applicationContext = context.applicationContext

    override fun intercept(chain: Interceptor.Chain): Response {
        if (!checkNetwork()) {
            if (preferencesProvider.boolean(applicationContext.getString(R.string.preference_failed_requests_notification), false)) {
                notificationCenter.postNotification(Notification("Network error: unable to proceed request\n${chain.request().url()}"))
            }
            throw NoConnectivityException()
        } else {
            return chain.proceed(chain.request())
        }
    }


    fun checkNetwork(): Boolean {
        val connectivity = applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
            ?: return false

        val networkInfo = connectivity.activeNetworkInfo
            ?: return false

        return networkInfo.isConnected
    }
}