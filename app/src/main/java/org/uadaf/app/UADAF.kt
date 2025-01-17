package org.uadaf.app

import android.app.Application
import com.google.gson.JsonParser
import com.jetradar.permissions.MrButler
import com.jetradar.permissions.PermissionsActivityDelegate
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton
import org.uadaf.app.dashboard.DashboardRepository
import org.uadaf.app.dashboard.impl.DashboardRepositoryImpl
import org.uadaf.app.internal.eventbus.EventBus
import org.uadaf.app.internal.eventbus.impl.EventBusImpl
import org.uadaf.app.internal.network.APIHelper
import org.uadaf.app.internal.network.ConnectivityInterceptor
import org.uadaf.app.internal.network.impl.APIHelperImpl
import org.uadaf.app.internal.service.ServiceFactory
import org.uadaf.app.internal.service.UADAFService
import org.uadaf.app.ith.ITHRepository
import org.uadaf.app.ith.impl.ITHRepositoryImpl
import org.uadaf.app.ith.service.ITHService
import org.uadaf.app.ith.service.ITHWebFetcherService
import org.uadaf.app.notificationcenter.NotificationCenter
import org.uadaf.app.notificationcenter.impl.NotificationCenterImpl
import org.uadaf.app.preferences.PreferencesProvider
import org.uadaf.app.preferences.impl.PreferencesProviderImpl
import org.uadaf.app.quoter.QuoterAPI
import org.uadaf.app.quoter.QuoterRepository
import org.uadaf.app.quoter.QuoterService
import org.uadaf.app.quoter.impl.QuoterAPIImpl
import org.uadaf.app.quoter.impl.QuoterRepositoryImpl
import pl.droidsonroids.retrofit2.JspoonConverterFactory

class UADAF : Application(), KodeinAware {

    private val apiURL = "http://52.48.142.75:6741/api/"
    private val ithURL = "https://ithappens.me/"
    private val quoterUrl = "http://52.48.142.75:6741/api/v2/quote/"


    // Create full-gray theme, special for Ника :)
    override val kodein: Kodein = Kodein {
        import(androidXModule(this@UADAF))

        bind<JsonParser>() with singleton { JsonParser() }

        bind<PreferencesProvider>() with singleton {
            PreferencesProviderImpl(
                context = instance()
            )
        }

        bind<DashboardRepository>() with singleton { DashboardRepositoryImpl() }

        bind<NotificationCenter>() with singleton {
            NotificationCenterImpl(
                context = instance()
            )
        }

        bind<PermissionsActivityDelegate>() with singleton { PermissionsActivityDelegate() }

        bind<MrButler>() with singleton {
            MrButler(
                permissionsHandler = instance()
            )
        }

        bind<ConnectivityInterceptor>() with singleton {
            ConnectivityInterceptor(
                context = instance(),
                notificationCenter = instance(),
                preferencesProvider = instance()
            )
        }

        bind<UADAFService>() with singleton {
            ServiceFactory.create<UADAFService>(
                baseURL = apiURL,
                interceptor = instance()
            )
        }

        bind<APIHelper>() with singleton {
            APIHelperImpl(
                context = instance(),
                service = instance()
            )
        }

        bind<QuoterService>() with singleton { ServiceFactory.create<QuoterService>(quoterUrl, instance()) }

        bind<QuoterAPI>() with singleton { QuoterAPIImpl(instance(), instance()) }

        bind<QuoterRepository>() with singleton {
            QuoterRepositoryImpl(
                quoterApi = instance()
            )
        }

        bind<ITHService>() with singleton {
            ServiceFactory.create<ITHService>(
                baseURL = apiURL + "ith/",
                interceptor = instance()
            )
        }

        bind<ITHWebFetcherService>() with singleton {
            ServiceFactory.create<ITHWebFetcherService>(
                baseURL = ithURL,
                interceptor = instance(),
                convertFactory = JspoonConverterFactory.create()
            )
        }

        bind<ITHRepository>() with singleton {
            ITHRepositoryImpl(
                service = instance(),
                fetcher = instance(),
                preferencesProvider = instance()
            )
        }

        bind<EventBus>() with singleton {
            EventBusImpl(instance())
        }
    }

}