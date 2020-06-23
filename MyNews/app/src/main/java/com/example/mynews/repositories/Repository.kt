package com.example.mynews.repositories

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.mynews.*
import com.example.mynews.api.formatDateForQuery
import com.example.mynews.api.formatNewsDeskFilters
import com.example.mynews.api.timesService
import com.example.mynews.notifications.TrackedSearchAlertRequest
import com.example.mynews.notifications.NotificationWorker
import com.example.mynews.notifications.Notifier
import org.joda.time.DateTime
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.TimeUnit

typealias TopStoriesResponse = com.example.mynews.models.topstories.Response
typealias MostPopularResponse = com.example.mynews.models.mostpopular.Response
typealias SearchResponse = com.example.mynews.models.search.Response

class Repository(val appContext: Context, val notifier: Notifier): IRepository {
    val TAG = this::class.java.simpleName
    private val _newsFeed = MutableLiveData<ArticlesOrError>()
    private val _articlesLoading = MutableLiveData<Boolean>()


    override fun getNewsFeed(): MutableLiveData<ArticlesOrError> { return _newsFeed }

    override fun articlesLoading(): MutableLiveData<Boolean> { return _articlesLoading }

    override fun getTopStories(){ loadFromTopStories(timesService.topStories(apiKey)) }

    override fun getArts() { loadFromTopStories(timesService.arts(apiKey)) }

    override fun getRealEstate() { loadFromTopStories(timesService.realEstate(apiKey)) }

    override fun getAutomobiles(){ loadFromTopStories(timesService.automobiles(apiKey)) }

    override fun getTechnology() { loadFromTopStories(timesService.technology(apiKey)) }

    override fun getMostPopular(){ loadFromMostPopular(timesService.mostPopular(apiKey)) }

    override fun getSearch(query: String?, beginDate:DateTime?, endDate:DateTime?,
                           newsDesks: Set<String>?)
    {

        val tracking =
            TrackedSearchAlertRequest(
                newsDesks, query,
                null, beginDate, endDate
            )

        val call =timesService.search(
            query=query,
            apiKey = apiKey,
            filters = formatNewsDeskFilters(newsDesks),
            beginDate = formatDateForQuery(beginDate),
            endDate = formatDateForQuery(endDate)
        )

        loadFromSearch(call, tracking)
    }


    private fun loadFromTopStories(call: Call<TopStoriesResponse> ) {
        _articlesLoading.value = true
        call.enqueue(object: Callback<TopStoriesResponse>{

            override fun onFailure(call: Call<TopStoriesResponse>, t: Throwable) {
                _newsFeed.value = Pair(t, null)
                _articlesLoading.value = false
            }

            override fun onResponse(call: Call<TopStoriesResponse>,
                                    response: retrofit2.Response<TopStoriesResponse>) {
                val body = response.body()
                if (body != null) {
                    _newsFeed.value = Pair(null, body.results)

                }
                _articlesLoading.value = false
            }
        })
    }

    private fun loadFromMostPopular(call: Call<MostPopularResponse>){
        _articlesLoading.value = true
        call.enqueue(object: Callback<MostPopularResponse> {

            override fun onFailure(call: Call<MostPopularResponse>, t: Throwable) {
                _newsFeed.value = Pair(t, null)
                _articlesLoading.value = false
            }

            override fun onResponse(call: Call<MostPopularResponse>,
                                    response: retrofit2.Response<MostPopularResponse>) {
                val body = response.body()
                if (body != null) {
                    _newsFeed.value = Pair(null, body.results)
                }
                _articlesLoading.value = false
            }
        })
    }

    private fun loadFromSearch(call: Call<SearchResponse>, tracking: TrackedSearchAlertRequest){
        _articlesLoading.value = true
        call.enqueue(object: Callback<SearchResponse> {
            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                _newsFeed.value = Pair(t, null)
                _articlesLoading.value = false
            }

            override fun onResponse(
                call: Call<SearchResponse>,
                response: Response<SearchResponse>
            ) {
                val body = response.body()
                val docs = body?.response?.docs
                if (docs != null) {
                    //only track workmanager searches for now.
//                    tracking.searchResult = docs
                    _newsFeed.value = Pair(null, docs)
//                    tracking.save(appContext)

                }
                _articlesLoading.value = false
            }
        })
    }





}


interface IRepository {
    fun articlesLoading(): MutableLiveData<Boolean>
    fun getNewsFeed(): MutableLiveData<ArticlesOrError>
    fun getTopStories()
    fun getArts()
    fun getRealEstate()
    fun getAutomobiles()
    fun getTechnology()
    fun getMostPopular()

    fun getSearch(
        query: String?,
        beginDate: DateTime?,
        endDate: DateTime?,
        newsDesks: Set<String>?)
}
