package com.amirahmadadibi.projects.myaparat.networking

import com.amirahmadadibi.projects.myaparat.model.UserVideos
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface RemoteApiService {
    @GET("api/videoByUser/username/{id}/perpage/10/curoffset/{page}")
    fun getVideos(
        @Path("id") username: String,
        @Path("page") numberOfItems: String
    ): Call<UserVideos>
}