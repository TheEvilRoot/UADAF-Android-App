package org.uadaf.app.internal.view.fabs;

import android.view.View;
import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.StringRes;

public class FABMode {

    @StringRes
    private int modeTitleRes;

    @DrawableRes
    private int modeDrawableRes;

    private FAB.OnClickListener modeClickListener;

    private FAB.OnLongClickListener modeLongClickListener;

    public FABMode(int modeTitleRes, int modeDrawableRes, FAB.OnClickListener modeClickListener) {
        this.modeTitleRes = modeTitleRes;
        this.modeDrawableRes = modeDrawableRes;
        this.modeClickListener = modeClickListener;
    }

    public FABMode(int modeTitleRes, int modeDrawableRes, FAB.OnClickListener modeClickListener, FAB.OnLongClickListener modeLongClickListener) {
        this(modeTitleRes, modeDrawableRes, modeClickListener);
        this.modeLongClickListener = modeLongClickListener;
    }

    public int getModeTitleRes() {
        return modeTitleRes;
    }

    public int getModeDrawableRes() {
        return modeDrawableRes;
    }

    public FAB.OnClickListener getModeClickListener() {
        return modeClickListener;
    }

    public FAB.OnLongClickListener getModeLongClickListener() {
        return modeLongClickListener;
    }

}
