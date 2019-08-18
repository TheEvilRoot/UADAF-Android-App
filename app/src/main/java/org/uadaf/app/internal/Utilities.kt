package org.uadaf.app.internal

import android.content.Context
import android.content.res.Resources
import android.util.TypedValue
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.annotation.StyleRes

@ColorInt
fun Context.themeColor(@AttrRes res: Int, thm: Resources.Theme = this.theme): Int {
    val typedValue = TypedValue()
    thm.resolveAttribute(res, typedValue, true)
    return typedValue.data
}