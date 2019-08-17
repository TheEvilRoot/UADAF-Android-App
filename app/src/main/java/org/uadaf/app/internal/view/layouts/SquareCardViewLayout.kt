package org.uadaf.app.internal.view.layouts

import android.content.Context
import android.util.AttributeSet
import androidx.cardview.widget.CardView

open class SquareCardViewLayout: CardView {

    constructor(context: Context): super(context)

    constructor(context: Context, attrs: AttributeSet): super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyles: Int): super(context, attrs, defStyles)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec)
    }

}