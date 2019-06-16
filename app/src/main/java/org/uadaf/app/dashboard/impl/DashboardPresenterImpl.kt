package org.uadaf.app.dashboard.impl

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import org.uadaf.app.dashboard.DashboardMenuItemView
import org.uadaf.app.dashboard.DashboardPresenter
import org.uadaf.app.dashboard.DashboardRepository
import org.uadaf.app.dashboard.DashboardView
import org.uadaf.app.notificationcenter.NotificationCenter
import org.uadaf.app.notificationcenter.data.Notification

class DashboardPresenterImpl (
    private val repository: DashboardRepository,
    private val dashboardView: DashboardView,
    private val notificationCenter: NotificationCenter
): DashboardPresenter{

    private val compositeDisposable = CompositeDisposable()

    override fun menuItemClick(position: Int, view: DashboardMenuItemView) {
        val item = repository.menuItem(position)
        dashboardView.displayFragment(item.fragmentRes)
    }

    override fun getMenuItemsCount(): Int =
        repository.menuItemsCount()

    override fun bindMenuItem(position: Int, view: DashboardMenuItemView) {
        val item = repository.menuItem(position)

        view.setTitle(item.titleRes)
        view.setImageDrawable(item.drawableRes)
    }

    override fun attachNotificationSubject() {
        compositeDisposable.add(notificationCenter
            .getNotifier()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                // No-Op
            },{
                notificationCenter.postNotification(Notification("Error: ${it.localizedMessage ?: "No-Op"}"))
            }))
    }

    override fun detachNotificationSubject() {
        compositeDisposable.clear()
    }


}