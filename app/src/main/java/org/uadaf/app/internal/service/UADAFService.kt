package org.uadaf.app.internal.service


import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface UADAFService {

    @GET("/{path}")
    fun api(
        @Path("path") path: String
    ): Call<ResponseBody>


}