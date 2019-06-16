package org.uadaf.app.notificationcenter.impl

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.notification_layout.view.*
import org.uadaf.app.notificationcenter.NotificationRowView

class NotificationViewHolder(itemView: View): RecyclerView.ViewHolder(itemView), NotificationRowView {

    override fun setText(text: String) {
        with(itemView) {
            titleView.text = text
        }
    }

}