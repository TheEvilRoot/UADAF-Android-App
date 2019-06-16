package org.uadaf.app.notificationcenter


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_notification_center.view.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance
import org.uadaf.app.R
import org.uadaf.app.internal.exceptions.ExceptionDispatcher
import org.uadaf.app.notificationcenter.impl.NotificationCenterPresenterImpl


class NotificationCenterFragment : Fragment(), NotificationCenterView, KodeinAware {

    override val kodein: Kodein by kodein()
    private val exceptionDispatcher: ExceptionDispatcher by instance()
    private val notificationCenter: NotificationCenter by instance()
    private val presenter: NotificationCenterPresenter by lazy { NotificationCenterPresenterImpl(notificationCenter, this, exceptionDispatcher) }
    private val adapter: NotificationCenterAdapter by lazy { NotificationCenterAdapter(presenter) }

    override fun displayNotificationList() {
        view?.run {
            noNotificationsGroup.visibility = View.INVISIBLE
            notificationsRecyclerView.visibility = View.VISIBLE
        }
    }

    override fun displayNoNotifications() {
        view?.run {
            noNotificationsGroup.visibility = View.VISIBLE
            notificationsRecyclerView.visibility = View.INVISIBLE
        }
    }

    override fun updateNewNotification(position: Int) {
        view?.run {
            adapter.notifyItemInserted(position)
        }
    }

    override fun updateDismissNotification(position: Int) {
        view?.run {
            adapter.notifyItemRemoved(position)
        }
    }

    override fun updateNotifications() {
        view?.run {
            adapter.notifyDataSetChanged()
        }
    }

    override fun navigateTo(fragmentRes: Int) {
        findNavController().navigate(fragmentRes)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_notification_center, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.attachToNotifier()
        with(view) {
            notificationsRecyclerView.layoutManager = LinearLayoutManager(view.context)
            notificationsRecyclerView.adapter = adapter
            val helper = ItemTouchHelper(object: ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
                override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean =
                    false

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    presenter.dismissNotification(viewHolder.adapterPosition)
                }

            })
            helper.attachToRecyclerView(notificationsRecyclerView)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.detachNotifier()
    }

    override fun displayError(message: String) {

    }


}
