package org.uadaf.app.dashboard

import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

interface DashboardMenuItemView {

    fun setTitle(@StringRes stringRes: Int)

    fun setImageDrawable(@DrawableRes drawableRes: Int)

}