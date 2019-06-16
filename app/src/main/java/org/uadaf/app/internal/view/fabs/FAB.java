package org.uadaf.app.internal.view.fabs;

import android.content.Context;
import android.graphics.Matrix;
import android.util.AttributeSet;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class FAB extends FloatingActionButton {

    private Matrix imageMatrix;

    public FAB(Context context) {
        super(context);
    }

    public FAB(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FAB(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        imageMatrix = getImageMatrix();
    }

    @Override
    public void setImageResource(int resId) {
        super.setImageResource(resId);
        setImageMatrix(imageMatrix);
    }
}
