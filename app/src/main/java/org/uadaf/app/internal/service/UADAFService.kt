package org.uadaf.app.internal.service


import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import org.uadaf.app.internal.network.ConnectivityInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface UADAFService {

    @GET("/{path}")
    fun api(
        @Path("path") path: String
    ): Call<ResponseBody>



}