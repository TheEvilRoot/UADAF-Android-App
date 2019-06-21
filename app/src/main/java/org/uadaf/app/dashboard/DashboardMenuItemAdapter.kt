package org.uadaf.app.dashboard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.uadaf.app.R
import org.uadaf.app.dashboard.impl.DashboardMenuItemViewHolder

class DashboardMenuItemAdapter(
    private val presenter: DashboardPresenter
) : RecyclerView.Adapter<DashboardMenuItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DashboardMenuItemViewHolder =
        DashboardMenuItemViewHolder(
            when (viewType) {
                0 -> LayoutInflater.from(parent.context).inflate(R.layout.dashboard_menu_item_layout, parent, false)
                else -> throw RuntimeException("Unimplemented view type in dashboard menu adapter")
            }
        )

    override fun getItemViewType(position: Int): Int {
        return 0
    }

    override fun getItemCount(): Int =
        presenter.getMenuItemsCount()

    override fun onBindViewHolder(holder: DashboardMenuItemViewHolder, position: Int) {
        presenter.bindMenuItem(position, holder)
        holder.itemView.setOnClickListener {
            presenter.menuItemClick(position, holder)
        }
    }

}