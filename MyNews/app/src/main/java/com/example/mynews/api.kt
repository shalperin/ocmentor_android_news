package com.example.mynews

import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface TimesService {
    @GET("/topstories/v2/home.json")
    fun topStoriesHome(): Call<Response>
}

val client = OkHttpClient().newBuilder().build()

val retrofit = Retrofit.Builder()
    .baseUrl(baseUrl)
    .client(client)
    .addConverterFactory(GsonConverterFactory.create())
    .build()

val timesService = retrofit.create(TimesService::class.java)