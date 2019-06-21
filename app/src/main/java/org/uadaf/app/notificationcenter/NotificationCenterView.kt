package org.uadaf.app.notificationcenter

import androidx.annotation.IdRes
import org.uadaf.app.internal.view.IErrorView

interface NotificationCenterView : IErrorView {

    fun displayNotificationList()

    fun displayNoNotifications()

    fun updateNewNotification(position: Int)

    fun updateDismissNotification(position: Int)

    fun updateNotifications()

    fun navigateTo(@IdRes fragmentRes: Int)

}