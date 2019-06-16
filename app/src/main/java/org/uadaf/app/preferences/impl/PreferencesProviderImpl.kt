package org.uadaf.app.preferences.impl

import android.content.Context
import androidx.preference.PreferenceManager
import org.uadaf.app.preferences.PreferencesProvider

class PreferencesProviderImpl(
    context: Context
): PreferencesProvider {

    private val applicationContext: Context = context.applicationContext

    override fun boolean(key: String, defaultValue: Boolean): Boolean =
        PreferenceManager.getDefaultSharedPreferences(applicationContext).getBoolean(key, defaultValue)

    override fun float(key: String, defaultValue: Float): Float =
        PreferenceManager.getDefaultSharedPreferences(applicationContext).getFloat(key, defaultValue)


    override fun int(key: String, defaultValue: Int): Int =
        PreferenceManager.getDefaultSharedPreferences(applicationContext).getInt(key, defaultValue)


    override fun long(key: String, defaultValue: Long): Long =
        PreferenceManager.getDefaultSharedPreferences(applicationContext).getLong(key, defaultValue)


    override fun string(key: String, defaultValue: String): String =
        PreferenceManager.getDefaultSharedPreferences(applicationContext).getString(key, defaultValue) ?: defaultValue


    override fun stringSet(key: String, defaultValue: MutableSet<String>): MutableSet<String> =
        PreferenceManager.getDefaultSharedPreferences(applicationContext).getStringSet(key, defaultValue) ?: defaultValue

    override fun preferenceName(res: Int): String =
            applicationContext.resources.getString(res)

}