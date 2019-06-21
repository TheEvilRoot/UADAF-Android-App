package org.uadaf.app.dashboard.data

import androidx.annotation.DrawableRes
import androidx.annotation.IdRes
import androidx.annotation.StringRes

class DashboardMenuItem(
    @StringRes
    val titleRes: Int,
    @DrawableRes
    val drawableRes: Int,
    @IdRes
    val fragmentRes: Int
)