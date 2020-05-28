package com.example.mynews

import com.example.mynews.models.topstories.Response
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface TimesService {
    @GET(topStoriesPath)
    fun topStories(@Query("api-key") apiKey:String): Call<Response>

    @GET(artsPath)
    fun arts(@Query("api-key") apiKey:String): Call<Response>

    @GET(mostPopularPath)
    fun mostPopular(@Query("api-key") apiKey:String): Call<Response>

    @GET(technologyPath)
    fun technology(@Query("api-key") apiKey:String): Call<Response>

    @GET(automobilesPath)
    fun automobiles(@Query("api-key") apiKey:String): Call<Response>

    @GET(realEstatePath)
    fun realEstate(@Query("api-key") apiKey:String): Call<Response>
}

val logging = HttpLoggingInterceptor().setLevel(logLevel)

val client = OkHttpClient()
    .newBuilder()
    .addInterceptor(logging)
    .build()

val retrofit = Retrofit.Builder()
    .baseUrl(baseUrl)
    .client(client)
    .addConverterFactory(GsonConverterFactory.create())
    .build()

val timesService = retrofit.create(TimesService::class.java)