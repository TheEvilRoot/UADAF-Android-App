package org.uadaf.app.notificationcenter.impl

import io.reactivex.disposables.CompositeDisposable
import org.uadaf.app.internal.exceptions.ExceptionDispatcher
import org.uadaf.app.notificationcenter.NotificationCenter
import org.uadaf.app.notificationcenter.NotificationCenterPresenter
import org.uadaf.app.notificationcenter.NotificationCenterView
import org.uadaf.app.notificationcenter.NotificationRowView
import org.uadaf.app.notificationcenter.data.Notification
import org.uadaf.app.notificationcenter.data.NotificationAction
import org.uadaf.app.notificationcenter.data.NotificationEvent

class NotificationCenterPresenterImpl (
    private val notificationCenter: NotificationCenter,
    private val view: NotificationCenterView,
    private val exceptionDispatcher: ExceptionDispatcher
): NotificationCenterPresenter {

    private val compositeDisposable = CompositeDisposable()

    override fun getNotificationsCount(): Int =
        notificationCenter.getNotificationCount()

    override fun getNotification(pos: Int): Notification =
        notificationCenter.getNotification(pos)

    override fun bindNotificationView(position: Int, view: NotificationRowView)  {
        val notification = notificationCenter.getNotification(position)
        view.setText(notification.text)
    }

    override fun clickNotification(notification: Notification, rowView: NotificationRowView) {
        if (notification.fragmentRes != null) {
            view.navigateTo(notification.fragmentRes)
        }
    }

    override fun dismissNotification(position: Int) {
        notificationCenter.dismissNotification(position)
    }

    override fun attachToNotifier() {
        updateView(NotificationEvent(NotificationAction.UPDATE))
        compositeDisposable.add(notificationCenter.getNotifier().subscribe({
            updateView(it)
        }, {
            exceptionDispatcher.dispatch(it, view)
        }))
    }

    private fun updateView(event: NotificationEvent) {
        when {
            event.action == NotificationAction.ADD -> view.updateNewNotification(event.position)
            event.action == NotificationAction.REMOVE -> view.updateDismissNotification(event.position)
            else -> view.updateNotifications()
        }
        if (getNotificationsCount() > 0) {
            view.displayNotificationList()
        } else {
            view.displayNoNotifications()
        }
    }

    override fun detachNotifier() {
        view.displayNotificationList()
        compositeDisposable.clear()
    }

}