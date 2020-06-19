package com.example.mynews

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface TimesService {
    @GET(topStoriesPath)
    fun topStories(@Query("api-key") apiKey:String):
            Call<com.example.mynews.models.topstories.Response>

    @GET(artsPath)
    fun arts(@Query("api-key") apiKey:String):
            Call<com.example.mynews.models.topstories.Response>

    @GET(mostPopularPath)
    fun mostPopular(@Query("api-key") apiKey:String):
            Call<com.example.mynews.models.mostpopular.Response>

    @GET(technologyPath)
    fun technology(@Query("api-key") apiKey:String):
            Call<com.example.mynews.models.topstories.Response>

    @GET(automobilesPath)
    fun automobiles(@Query("api-key") apiKey:String):
            Call<com.example.mynews.models.topstories.Response>

    @GET(realEstatePath)
    fun realEstate(@Query("api-key") apiKey:String):
            Call<com.example.mynews.models.topstories.Response>

    //todo this needs to support a few other fields.
    @GET(searchPath)
    fun search(
        @Query("query") query:String?,
        @Query( "api-key") apiKey:String,
        @Query("fq" ) filters:String?, // fq:news_desk("Sports" "Arts") see NYT api docs
        @Query("begin_date") beginDate: String?,
        @Query("end_date") endDate: String?,
        @Query("sort") sort: String? = apiSortOrderRelevance
    ):
            Call<com.example.mynews.models.search.Response>

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