package org.uadaf.app.notificationcenter.data

import androidx.annotation.IdRes

data class Notification(
    val text: String,
    @IdRes val fragmentRes: Int? = null
)