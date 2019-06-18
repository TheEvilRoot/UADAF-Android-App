package org.uadaf.app.about.data

import android.graphics.Color
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes

data class Link (
    val label: String,
    val url: String,
    @ColorRes val color: Int,
    @DrawableRes val icon: Int
)