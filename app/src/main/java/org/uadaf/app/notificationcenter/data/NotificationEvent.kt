package org.uadaf.app.notificationcenter.data

enum class NotificationAction {
    ADD,
    REMOVE,
    UPDATE
}

data class NotificationEvent(
    val action: NotificationAction,
    val position: Int = 0,
    val notification: Notification? = null
)