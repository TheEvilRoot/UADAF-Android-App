package org.uadaf.app.notificationcenter.impl

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.content.getSystemService
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject
import org.reactivestreams.Subscriber
import org.uadaf.app.notificationcenter.NotificationCenter
import org.uadaf.app.notificationcenter.data.Notification
import org.uadaf.app.notificationcenter.data.NotificationAction
import org.uadaf.app.notificationcenter.data.NotificationEvent

class NotificationCenterImpl(
    context: Context
): NotificationCenter {

    private val applicationContext = context.applicationContext

    private val notificationList: ArrayList<Notification> = ArrayList()

    private val notificationSubject: PublishSubject<NotificationEvent> = PublishSubject.create()

    override fun getNotifications(): List<Notification> =
        notificationList.toList()

    override fun getNotification(pos: Int): Notification =
        notificationList[pos]

    override fun getNotificationCount(): Int =
        notificationList.count()

    override fun getIndexOf(notification: Notification): Int =
        notificationList.indexOf(notification)

    override fun postNotification(notification: Notification) {
        notificationList.add(notification)
        postAndroidNotification(notification)
        notificationSubject.onNext(NotificationEvent(NotificationAction.ADD, getNotificationCount() - 1, notification))
    }

    override fun dismissNotification(position: Int) {
        val notification = getNotification(position)
        dismissAndroidNotification(notification)
        notificationList.removeAt(position)
        notificationSubject.onNext(NotificationEvent(NotificationAction.REMOVE, position, notification))
    }

    private fun postAndroidNotification(notification: Notification) {
        // TODO("not implemented")
    }

    private fun dismissAndroidNotification(notification: Notification) {
        // TODO("not implemented")
    }

    override fun getNotifier(): Subject<NotificationEvent> =
            notificationSubject


}