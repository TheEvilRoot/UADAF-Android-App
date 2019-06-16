package org.uadaf.app.dashboard
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.fragment_dashboard.view.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance
import org.uadaf.app.R
import org.uadaf.app.dashboard.impl.DashboardPresenterImpl
import org.uadaf.app.notificationcenter.NotificationCenter

class DashboardFragment : Fragment(), KodeinAware, DashboardView {
    override val kodein: Kodein by kodein()

    private val repository: DashboardRepository by instance()
    private val notificationCenter: NotificationCenter by instance()
    private val presenter: DashboardPresenter by lazy { DashboardPresenterImpl(repository, this, notificationCenter) }
    private val adapter: DashboardMenuItemAdapter by lazy { DashboardMenuItemAdapter(presenter) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.attachNotificationSubject()
        view.apply {
            dashboardRecyclerView.layoutManager = GridLayoutManager(context, presenter.getMenuItemsCount() / 2)
            dashboardRecyclerView.adapter = adapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.detachNotificationSubject()
    }

    override fun displayFragment(fragmentRes: Int) {
        view?.run {
            val navController = Navigation.findNavController(this)
            navController.navigate(fragmentRes)
        }
    }

}
