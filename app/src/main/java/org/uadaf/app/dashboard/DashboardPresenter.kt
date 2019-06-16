package org.uadaf.app.dashboard

interface DashboardPresenter {

    fun getMenuItemsCount(): Int

    fun bindMenuItem(position: Int, view: DashboardMenuItemView)

    fun menuItemClick(position: Int, view: DashboardMenuItemView)

    fun attachNotificationSubject()

    fun detachNotificationSubject()

}