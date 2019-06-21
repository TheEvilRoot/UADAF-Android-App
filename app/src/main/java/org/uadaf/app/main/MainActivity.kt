package org.uadaf.app.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.gmail.samehadar.iosdialog.IOSDialog
import com.jetradar.permissions.PermissionsActivityDelegate
import kotlinx.android.synthetic.main.activity_main.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton
import org.uadaf.app.R
import org.uadaf.app.UADAF
import org.uadaf.app.internal.exceptions.ExceptionDispatcher
import org.uadaf.app.internal.exceptions.impl.ExceptionDispatcherImpl
import org.uadaf.app.main.impl.MainPresenterImpl
import org.uadaf.app.members.MembersFragment

class MainActivity : AppCompatActivity(),
    MembersFragment.OnFragmentInteractionListener,
    KodeinAware, MainView {

    override val kodein: Kodein by Kodein.lazy {
        extend((application as UADAF).kodein)
        bind<MainView>() with singleton { this@MainActivity }
        bind<MainPresenter>() with singleton {
            MainPresenterImpl(
                instance(),
                instance(),
                instance(),
                this@MainActivity
            )
        }
        bind<ExceptionDispatcher>() with singleton {
            ExceptionDispatcherImpl(
                instance(),
                instance(),
                instance(),
                instance()
            )
        }
    }

    private val permissionsDelegate: PermissionsActivityDelegate by instance()

    // I Will refactor this later ;)
    private val loadingDialog: IOSDialog by lazy {
        val dialog = IOSDialog.Builder(this)
        dialog.setTitle("Loading...")
        dialog.build()
    }

    private val presenter: MainPresenter by instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        permissionsDelegate.attach(this)
        presenter.prepare()
        setupNavigation()
    }

    private fun setupNavigation() {
        val navController = findNavController(R.id.mainNavigationFragmentHost)
        NavigationUI.setupWithNavController(bottomNavigationView, navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return Navigation.findNavController(this, R.id.mainNavigationFragmentHost).navigateUp()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        permissionsDelegate.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onDestroy() {
        super.onDestroy()
        permissionsDelegate.detach()
        presenter.dispose()
    }

    override fun displayLoading() {
        loadingDialog.show()
    }

    override fun stopLoading() {
        if (loadingDialog.isShowing) {
            loadingDialog.dismiss()
        }
    }

    override fun updateNavigationBadges() {

    }

    override fun displayFatalError(message: String, throwable: Throwable) {
        TODO("fatalError") //To change body of created functions use File | Settings | File Templates.
    }


}