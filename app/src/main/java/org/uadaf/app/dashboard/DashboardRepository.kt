package org.uadaf.app.dashboard

import org.uadaf.app.dashboard.data.DashboardMenuItem

interface DashboardRepository {

    fun menuItemsCount(): Int

    fun menuItem(pos: Int): DashboardMenuItem

}