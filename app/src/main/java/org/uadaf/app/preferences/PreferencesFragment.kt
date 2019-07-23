package org.uadaf.app.preferences

import android.os.Bundle
import android.view.View
import androidx.preference.PreferenceFragmentCompat
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance
import org.uadaf.app.R
import org.uadaf.app.internal.eventbus.EventBus
import org.uadaf.app.internal.eventbus.EventType

class PreferencesFragment : PreferenceFragmentCompat(), KodeinAware{

    override val kodein: Kodein by kodein()
    private val preferencesProvider: PreferencesProvider by instance()
    private val eventBus: EventBus by instance()

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.app_preferences)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        preferenceManager.sharedPreferences.registerOnSharedPreferenceChangeListener { _, key ->
            if (key == preferencesProvider.preferenceName(R.string.preference_ith_username)) {
                eventBus.dispatch(EventType.ITH_NAME_CHANGED)
            }
        }
    }

}
