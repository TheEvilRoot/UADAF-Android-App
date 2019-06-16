package org.uadaf.app.ith.service

import org.uadaf.app.ith.data.ITHStory
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ITHWebFetcherService {

    fun last()

    @GET("/story/{id}")
    fun story(
        @Path("id") id: Int
    ): Call<ITHStory>

}