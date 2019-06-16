package org.uadaf.app.internal.view.layouts;

import android.content.Context;
import android.util.AttributeSet;
import androidx.cardview.widget.CardView;

public class SquareCardViewLayout extends CardView {
    public SquareCardViewLayout(Context context) {
        super(context);
    }

    public SquareCardViewLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SquareCardViewLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }
}
