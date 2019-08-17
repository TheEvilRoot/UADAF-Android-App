package org.uadaf.app.internal.view.fabs

import android.content.Context
import android.graphics.Matrix
import android.util.AttributeSet
import com.google.android.material.floatingactionbutton.FloatingActionButton

open class FAB: FloatingActionButton {

    private var mMatrix: Matrix? = null

    constructor(context: Context): super(context)

    constructor(context: Context, attrs: AttributeSet): super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyles: Int): super(context, attrs, defStyles)

    override fun onFinishInflate() {
        super.onFinishInflate()
        mMatrix = imageMatrix
    }

    override fun setImageResource(resId: Int) {
        super.setImageResource(resId)
        imageMatrix = mMatrix
    }

}