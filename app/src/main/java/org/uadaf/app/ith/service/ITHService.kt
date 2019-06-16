package org.uadaf.app.ith.service

import okhttp3.ResponseBody
import org.uadaf.app.ith.data.ITHUser
import retrofit2.Call
import retrofit2.http.*

interface ITHService {

    @GET("login/{name}")
    fun login (
        @Path("name") username: String
    ): Call<ITHUser>

    @FormUrlEncoded
    @POST("set")
    fun set(
        @Field("username") username: String,
        @Field("storyId") userId: String
    ): Call<ResponseBody>

}