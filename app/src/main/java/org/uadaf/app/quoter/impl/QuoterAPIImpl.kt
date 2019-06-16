package org.uadaf.app.quoter.impl

import org.uadaf.app.quoter.QuoterAPI
import quoter.Quoter
import java.lang.RuntimeException

class QuoterAPIImpl (

): QuoterAPI {

    private val baseUrl = "http://52.48.142.75:6741/api/v2/quote/"
    private val keyLifetime = 5 * 60 * 1000

    private var quoter: Quoter? = null
    private var hasKey: Boolean = false
    private var keyTime: Long = -1

    override fun quoter(): Quoter {
        if (quoter == null || (hasKey && keyTime + keyLifetime < System.currentTimeMillis())) {
            quoter = Quoter(baseUrl, null)
            hasKey = false
            keyTime = -1
            return quoter
                ?: throw RuntimeException("Unable to create Quoter(baseUrl, null)")
        }

        return quoter ?:
                throw RuntimeException("Quoter unexpectedly became null")
    }

    override fun hasValidKey(): Boolean =
        hasKey && keyTime + keyLifetime > System.currentTimeMillis()

    override fun applyKey(key: String) {
        hasKey = true
        keyTime = System.currentTimeMillis()
        quoter = Quoter(baseUrl, key)
    }
}