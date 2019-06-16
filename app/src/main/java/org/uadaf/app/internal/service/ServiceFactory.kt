package org.uadaf.app.internal.service

import okhttp3.OkHttpClient
import org.uadaf.app.internal.network.ConnectivityInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object ServiceFactory {

    inline fun <reified T> create(baseURL: String, interceptor: ConnectivityInterceptor, convertFactory: Converter.Factory = GsonConverterFactory.create()): T {

        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .client(client)
            .baseUrl(baseURL)
            .addConverterFactory(convertFactory)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

        return retrofit.create(T::class.java)
    }

}