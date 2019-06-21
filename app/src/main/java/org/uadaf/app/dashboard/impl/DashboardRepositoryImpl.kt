package org.uadaf.app.dashboard.impl

import org.uadaf.app.R
import org.uadaf.app.dashboard.DashboardRepository
import org.uadaf.app.dashboard.data.DashboardMenuItem

class DashboardRepositoryImpl : DashboardRepository {

    private val menuItems = arrayOf(
        DashboardMenuItem(R.string.quoter_title, R.drawable.ic_format_quote, R.id.quoterFragment),
        DashboardMenuItem(R.string.members_title, R.drawable.ic_people, R.id.membersFragment),
        DashboardMenuItem(R.string.ith_title, R.drawable.ic_insert_comment, R.id.ITHFragment),
        DashboardMenuItem(R.string.service_status_title, R.drawable.ic_settings_ethernet, R.id.serviceStatusFragment),
        DashboardMenuItem(R.string.preferences_title, R.drawable.ic_settings, R.id.preferencesFragment),
        DashboardMenuItem(R.string.about_title, R.drawable.ic_info, R.id.aboutFragment)
    )

    override fun menuItemsCount(): Int =
        menuItems.count()

    override fun menuItem(pos: Int): DashboardMenuItem =
        menuItems[pos]

}