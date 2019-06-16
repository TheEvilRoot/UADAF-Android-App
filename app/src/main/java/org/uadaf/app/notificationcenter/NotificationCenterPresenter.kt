package org.uadaf.app.notificationcenter

import org.uadaf.app.notificationcenter.data.Notification


interface NotificationCenterPresenter {

    fun getNotificationsCount(): Int

    fun getNotification(pos: Int): Notification

    fun bindNotificationView(position: Int, view: NotificationRowView)

    fun clickNotification(notification: Notification, rowView: NotificationRowView)

    fun dismissNotification(position: Int)

    fun attachToNotifier()

    fun detachNotifier()

}