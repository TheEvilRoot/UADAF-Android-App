package org.uadaf.app.internal.view.fabs

import android.view.View
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

class FABMode (
    @StringRes
    val modeTitleRes: Int,
    @DrawableRes
    val modeDrawableRes: Int,
    val modeClickListener: View.OnClickListener = View.OnClickListener {  },
    val modeLongClickListener: View.OnLongClickListener = View.OnLongClickListener { false }
)