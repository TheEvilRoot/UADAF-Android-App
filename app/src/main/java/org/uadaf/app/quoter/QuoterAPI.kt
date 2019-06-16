package org.uadaf.app.quoter

import quoter.Quoter

interface QuoterAPI {

    fun quoter(): Quoter

    fun hasValidKey(): Boolean

    fun applyKey(key: String)

}