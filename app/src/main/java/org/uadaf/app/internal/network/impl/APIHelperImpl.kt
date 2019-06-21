package org.uadaf.app.internal.network.impl

import android.content.Context
import org.uadaf.app.internal.NoConnectivityException
import org.uadaf.app.internal.network.APIHelper
import org.uadaf.app.internal.service.UADAFService

class APIHelperImpl(
    context: Context,
    private val service: UADAFService
) : APIHelper {

    private val applicationContext = context.applicationContext

    override fun checkService(): Boolean {
        return try {
            val call = service.api("")
            val response = call.execute()

            response.code() == 404
        } catch (e: NoConnectivityException) {
            false
        }
    }

}