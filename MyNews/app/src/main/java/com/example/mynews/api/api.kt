package com.example.mynews.api

import com.example.mynews.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface TimesService {
    @GET(TOP_STORIES_PATH)
    fun topStories(@Query("api-key") apiKey:String):
            Call<com.example.mynews.models.topstories.Response>

    @GET(ARTS_PATH)
    fun arts(@Query("api-key") apiKey:String):
            Call<com.example.mynews.models.topstories.Response>

    @GET(MOST_POPULAR_PATH)
    fun mostPopular(@Query("api-key") apiKey:String):
            Call<com.example.mynews.models.mostpopular.Response>

    @GET(TECHNOLOGY_PATH)
    fun technology(@Query("api-key") apiKey:String):
            Call<com.example.mynews.models.topstories.Response>

    @GET(AUTOMOBILES_PATH)
    fun automobiles(@Query("api-key") apiKey:String):
            Call<com.example.mynews.models.topstories.Response>

    @GET(REAL_ESTATE_PATH)
    fun realEstate(@Query("api-key") apiKey:String):
            Call<com.example.mynews.models.topstories.Response>

    //todo this needs to support a few other fields.
    @GET(SEARCH_PATH)
    fun search(
        @Query("query") query:String?,
        @Query( "api-key") apiKey:String,
        @Query("fq" ) filters:String?, // fq:news_desk("Sports" "Arts") see NYT api docs
        @Query("begin_date") beginDate: String?,
        @Query("end_date") endDate: String?,
        @Query("sort") sort: String? = API_SORT_ORDER_RELEVANCE
    ):
            Call<com.example.mynews.models.search.Response>

}

val logging = HttpLoggingInterceptor().setLevel(logLevel)

val client = OkHttpClient()
    .newBuilder()
    .addInterceptor(logging)
    .build()

val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .client(client)
    .addConverterFactory(GsonConverterFactory.create())
    .build()

val timesService = retrofit.create(TimesService::class.java)