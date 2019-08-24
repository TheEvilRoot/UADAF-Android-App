package org.uadaf.app.quoter

import io.ktor.client.engine.okhttp.OkHttpConfig
import quoter.Quoter

interface QuoterAPI {

    fun quoter(): QuoterService

    fun hasValidKey(): Boolean

    fun applyKey(key: String)

}