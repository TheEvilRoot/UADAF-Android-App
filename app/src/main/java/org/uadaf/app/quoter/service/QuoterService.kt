package org.uadaf.app.quoter.service

import io.reactivex.Single
import org.uadaf.app.quoter.data.Quote
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface QuoterService {

    @GET("pos/{pos}")
    fun getByPos(
        @Path("pos") pos: Int
    ): Call<Quote>

    @GET("random/{count}")
    fun getByRandom(
        @Path("count") count: Int = 1
    ): Call<List<Quote>>

    @GET("all")
    fun getAll(): Call<List<Quote>>

    @GET("total")
    fun getTotal(): Call<Int>

}