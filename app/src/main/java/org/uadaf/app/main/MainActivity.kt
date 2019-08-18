package org.uadaf.app.main

import android.content.res.ColorStateList
import android.graphics.drawable.TransitionDrawable
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.gmail.samehadar.iosdialog.IOSDialog
import com.jetradar.permissions.PermissionsActivityDelegate
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_main.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton
import org.uadaf.app.R
import org.uadaf.app.UADAF
import org.uadaf.app.internal.eventbus.EventBus
import org.uadaf.app.internal.eventbus.EventType
import org.uadaf.app.internal.eventbus.impl.BaseEventAction
import org.uadaf.app.internal.exceptions.ExceptionDispatcher
import org.uadaf.app.internal.exceptions.impl.ExceptionDispatcherImpl
import org.uadaf.app.internal.themeColor
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

    private val navForegroundPrimary: Int
        get() = R.attr.colorPrimary
    private val navForegroundSecondary: Int
        get() = R.attr.colorNavIcons

    /**
     * direction:
     *  @true if destination has light navigation background
     *  @false if destination is dashboard
     */
    private val destinationChangedListener = NavController.OnDestinationChangedListener { navController, navDestination, _ ->
        if (navDestination.id != R.id.dashboardFragment) {
            changeNavigationViewTheme(true, navForegroundPrimary)
        } else {
            changeNavigationViewTheme(false, navForegroundSecondary)
        }
    }

    private var currentDirection: Boolean = false

    private val presenter: MainPresenter by instance()
    private val eventBus: EventBus by instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        permissionsDelegate.attach(this)
        presenter.prepare()
        setupNavigation()
        changeNavigationViewTheme(false, navForegroundSecondary)
        eventBus.registerHandler(EventType.ITH_NAME_CHANGED, BaseEventAction(AndroidSchedulers.mainThread()) {
            Toast.makeText(applicationContext(), "Hello", Toast.LENGTH_SHORT).show()
        })
    }

    private fun setupNavigation() {
        val navController = findNavController(R.id.mainNavigationFragmentHost)
        NavigationUI.setupWithNavController(bottomNavigationView, navController)
    }

    private fun changeNavigationViewTheme(direction: Boolean, @AttrRes foreground: Int) {
        if (direction == currentDirection) return

        @ColorInt
        val fgColor = themeColor(foreground)

        bottomNavigationView.run {
            @ColorInt
            val oldFgColor = bottomNavigationView.itemTextColor?.defaultColor ?: fgColor

            val newFg = ColorStateList.valueOf(fgColor)

            val transition = background as TransitionDrawable
            if (direction) transition.startTransition(300) else transition.reverseTransition(300)

            itemTextColor = newFg
            itemIconTintList = newFg
        }

        currentDirection = direction
    }

    override fun onResume() {
        super.onResume()
        findNavController(R.id.mainNavigationFragmentHost).addOnDestinationChangedListener(destinationChangedListener)
    }

    override fun onPause() {
        super.onPause()
        findNavController(R.id.mainNavigationFragmentHost).removeOnDestinationChangedListener(destinationChangedListener)
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