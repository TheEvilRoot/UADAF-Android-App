package org.uadaf.app.internal.view.fabs

import android.content.Context
import android.os.Build
import android.util.AttributeSet

class ModesFAB : FAB {

    var currentMode: FABMode? = null
        private set

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    fun switchMode(current: FABMode) {
        this.setOnClickListener(current.modeClickListener)
        this.setOnLongClickListener(current.modeLongClickListener)
        this.setImageDrawable(context.resources.getDrawable(current.modeDrawableRes))

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            this.tooltipText = context.resources.getString(current.modeTitleRes)
        }

        this.fixImage()
        this.currentMode = current
    }

    private fun fixImage() {
        this.hide()
        this.show()
    }


}
