package com.amirahmadadibi.projects.myaparat.networking

import com.amirahmadadibi.projects.myaparat.BASE_URL
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


fun buildClient(): OkHttpClient =
        OkHttpClient.Builder().build()

fun buildRetrofit(): Retrofit {
    return Retrofit.Builder()
            .client(buildClient())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
}

fun getApiService(): RemoteApiService =
        buildRetrofit().create(RemoteApiService::class.java)
