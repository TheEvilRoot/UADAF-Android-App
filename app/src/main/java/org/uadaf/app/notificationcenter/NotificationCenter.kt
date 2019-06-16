package org.uadaf.app.notificationcenter

import io.reactivex.subjects.Subject
import org.uadaf.app.notificationcenter.data.Notification
import org.uadaf.app.notificationcenter.data.NotificationEvent

interface NotificationCenter {

    fun getNotifications(): List<Notification>

    fun getIndexOf(notification: Notification): Int

    fun getNotification(pos: Int): Notification

    fun getNotificationCount(): Int

    fun postNotification(notification: Notification)

    fun dismissNotification(position: Int)

    fun getNotifier(): Subject<NotificationEvent>

}