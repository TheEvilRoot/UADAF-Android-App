package org.uadaf.app.preferences

import androidx.annotation.StringRes

interface PreferencesProvider {

    fun boolean(key: String, defaultValue: Boolean): Boolean
    fun float(key: String, defaultValue: Float): Float
    fun int(key: String, defaultValue: Int): Int
    fun long(key: String, defaultValue: Long): Long
    fun string(key: String, defaultValue: String): String
    fun stringSet(key: String, defaultValue: MutableSet<String>): MutableSet<String>

    fun preferenceName(@StringRes res: Int): String

}