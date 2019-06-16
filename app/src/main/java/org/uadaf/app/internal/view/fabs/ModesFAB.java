package org.uadaf.app.internal.view.fabs;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.util.AttributeSet;
import androidx.annotation.ColorInt;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.view.ViewCompat;
import org.uadaf.app.R;

public class ModesFAB extends FAB{

    private FABMode current;

    public ModesFAB(Context context) {
        super(context);
    }

    public ModesFAB(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ModesFAB(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * if (shown) {
     *             fabAdd.show()
     *             fabSearch.show()
     *             fabMenu.setImageDrawable(ContextCompat.getDrawable(fabMenu.context, R.drawable.ic_close))
     *
     *             fabMenu.setOnClickListener {
     *                 setMenuState(false)
     *             }
     *         } else {
     *             fabAdd.hide()
     *             fabSearch.hide()
     *             fabMenu.setImageDrawable(ContextCompat.getDrawable(fabMenu.context, R.drawable.ic_menu))
     *             fabMenu.setOnClickListener {
     *                 setMenuState(true)
     *             }
     *         }
     */

    public void switchMode(FABMode current) {
        if (current == null) {
            throw new RuntimeException("Unable to switch to null mode");
        }

        this.setOnClickListener(current.getModeClickListener());
        this.setOnLongClickListener(current.getModeLongClickListener());
        this.setImageDrawable(getContext().getResources().getDrawable(current.getModeDrawableRes()));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            this.setTooltipText(getContext().getResources().getString(current.getModeTitleRes()));
        }
        this.fixImage();
        this.current = current;
    }

    public void fixImage() {
        this.hide();
        this.show();
    }


    public FABMode getCurrentMode() {
        return current;
    }


}
