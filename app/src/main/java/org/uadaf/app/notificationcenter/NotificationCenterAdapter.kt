package org.uadaf.app.notificationcenter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.uadaf.app.R
import org.uadaf.app.notificationcenter.impl.NotificationViewHolder

class NotificationCenterAdapter(
    private val presenter: NotificationCenterPresenter
) : RecyclerView.Adapter<NotificationViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationViewHolder =
        when (viewType) {
            0 -> NotificationViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.notification_layout,
                    parent,
                    false
                )
            )
            else -> throw RuntimeException("Unimplemented view type of notification center adapter")
        }

    override fun getItemCount(): Int =
        presenter.getNotificationsCount()

    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) {
        presenter.bindNotificationView(position, holder)
        val notification = presenter.getNotification(position)
        holder.itemView.setOnClickListener {
            presenter.clickNotification(notification, holder)
        }
    }
}