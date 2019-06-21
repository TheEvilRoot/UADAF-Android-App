package org.uadaf.app.dashboard

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

interface DashboardMenuItemView {

    fun setTitle(@StringRes stringRes: Int)

    fun setImageDrawable(@DrawableRes drawableRes: Int)

}