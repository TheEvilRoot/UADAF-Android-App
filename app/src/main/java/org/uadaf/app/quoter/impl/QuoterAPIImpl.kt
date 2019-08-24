package org.uadaf.app.quoter.impl

import org.uadaf.app.internal.network.ConnectivityInterceptor
import org.uadaf.app.quoter.QuoterAPI
import org.uadaf.app.quoter.QuoterService

class QuoterAPIImpl(
    private val interceptor: ConnectivityInterceptor,
    private val service: QuoterService
) : QuoterAPI {

    private val keyLifetime = 5 * 60 * 1000

    private var hasKey: Boolean = false
    private var keyTime: Long = -1

    override fun quoter(): QuoterService {
        if (hasKey && keyTime + keyLifetime < System.currentTimeMillis()) {
            hasKey = false
            keyTime = -1
            return service
        }
        return service
    }

    override fun hasValidKey(): Boolean =
        hasKey && keyTime + keyLifetime > System.currentTimeMillis()

    override fun applyKey(key: String) {
        hasKey = true
        keyTime = System.currentTimeMillis()
    }
}