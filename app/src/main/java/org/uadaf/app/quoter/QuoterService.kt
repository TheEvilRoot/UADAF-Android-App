package org.uadaf.app.quoter

import quoter.Quote
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface QuoterService {

    @Headers("Connection: close")
    @GET("all")
    fun all(@Query("resolver") resolver: String): Call<List<Quote>>

    @Headers("Connection: close")
    @GET("total")
    fun total(@Query("resolver") resolver: String): Call<Int>

}