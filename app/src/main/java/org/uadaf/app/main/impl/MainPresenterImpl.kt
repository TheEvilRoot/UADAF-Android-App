package org.uadaf.app.main.impl

import com.jetradar.permissions.MrButler
import com.jetradar.permissions.PermissionsDeniedException
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.BiFunction
import io.reactivex.functions.Function3
import io.reactivex.internal.functions.Functions
import io.reactivex.schedulers.Schedulers
import org.uadaf.app.R
import org.uadaf.app.internal.NoConnectivityException
import org.uadaf.app.internal.network.APIHelper
import org.uadaf.app.main.MainPresenter
import org.uadaf.app.main.MainView
import org.uadaf.app.notificationcenter.NotificationCenter
import org.uadaf.app.notificationcenter.data.Notification

class MainPresenterImpl (
    private val notificationCenter: NotificationCenter,
    private val mrButler: MrButler,
    private val apiHelper: APIHelper,
    private val view: MainView
): MainPresenter {

    private val compositeDisposable = CompositeDisposable()

    override fun prepare() {
        view.displayLoading()
        compositeDisposable.add(
            Single.zip(
                // Zip service check and permissions check
                mrButler.require(true,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    android.Manifest.permission.ACCESS_NETWORK_STATE,
                    android.Manifest.permission.INTERNET).toSingle { true },

                Single.fromCallable <Boolean> { apiHelper.checkService() },

                // But relevant only service check result because permission check always emits true or an exception
                BiFunction<Boolean, Boolean, Boolean> { your_decision, her_decision -> her_decision } // :D
            )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                view.stopLoading()
                if (!it) {
                    notificationCenter.postNotification(Notification("Service is unavailable", R.id.serviceStatusFragment))
                }
            }, {
                view.stopLoading()
                if (it is PermissionsDeniedException) {
                    notificationCenter.postNotification(Notification("Error: permission denied"))
                } else if (it is NoConnectivityException) {
                    notificationCenter.postNotification(Notification("Service is unavailable because of exception: ${it.localizedMessage}"))
                }
            })
        )

    }

    override fun dispose() {
        compositeDisposable.clear()
    }

}