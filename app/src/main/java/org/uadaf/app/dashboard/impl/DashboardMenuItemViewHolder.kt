package org.uadaf.app.dashboard.impl

import android.view.View
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.dashboard_menu_item_layout.view.*
import org.uadaf.app.dashboard.DashboardMenuItemView

class DashboardMenuItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), DashboardMenuItemView {
    override fun setTitle(@StringRes stringRes: Int) {
        with(itemView) {
            titleView.setText(stringRes)
        }
    }

    override fun setImageDrawable(@DrawableRes drawableRes: Int) {
        with(itemView) {
            imageView.setImageDrawable(itemView.context.getDrawable(drawableRes))
        }
    }

}