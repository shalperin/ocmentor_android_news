package com.example.mynews

import androidx.lifecycle.MutableLiveData
import org.joda.time.DateTime
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

typealias TopStoriesResponse = com.example.mynews.models.topstories.Response
typealias MostPopularResponse = com.example.mynews.models.mostpopular.Response
typealias SearchResponse = com.example.mynews.models.search.Response

class Repository :IRepository {

    private val _newsFeed = MutableLiveData<ArticlesOrError>()

    override fun getNewsFeed(): MutableLiveData<ArticlesOrError> {
        return _newsFeed
    }

    override fun getTopStories(){
        loadFromTopStories(timesService.topStories(apiKey))
    }

    override fun getArts() {
        loadFromTopStories(timesService.arts(apiKey))
    }

    override fun getRealEstate() {
        loadFromTopStories(timesService.realEstate(apiKey))
    }

    override fun getAutomobiles(){
        loadFromTopStories(timesService.automobiles(apiKey))
    }

    override fun getTechnology() {
        loadFromTopStories(timesService.technology(apiKey))
    }

    override fun getMostPopular(){
        loadFromMostPopular(timesService.mostPopular(apiKey))
    }

    //Todo this only supports term right now.
    override fun getSearch(query: String?, beginDate:DateTime?, endDate:DateTime?, newsDesks: List<String>?)
    {
        loadFromSearch(timesService.search(
            query=query,
            apiKey = apiKey ,
            filters = formatNewsDeskFilters(newsDesks),
            beginDate = formatDateForQuery(beginDate),
            endDate = formatDateForQuery(endDate)
        ))


    }

    fun loadFromTopStories(call: Call<TopStoriesResponse>) {
        val callback =

        call.enqueue(object: Callback<TopStoriesResponse>{

            override fun onFailure(call: Call<TopStoriesResponse>, t: Throwable) {
                _newsFeed.value = Pair(t, null)
            }

            override fun onResponse(call: Call<TopStoriesResponse>,
                                    response: retrofit2.Response<TopStoriesResponse>) {
                val body = response.body()
                if (body != null) {
                    _newsFeed.value = Pair(null, body.results)
                }
            }
        })
    }

    fun loadFromMostPopular(call: Call<MostPopularResponse>){
        call.enqueue(object: Callback<MostPopularResponse> {

            override fun onFailure(call: Call<MostPopularResponse>, t: Throwable) {
                _newsFeed.value = Pair(t, null)
            }

            override fun onResponse(call: Call<MostPopularResponse>,
                                    response: retrofit2.Response<MostPopularResponse>) {
                val body = response.body()
                if (body != null) {
                    _newsFeed.value = Pair(null, body.results)
                }
            }
        })
    }

    fun loadFromSearch(call: Call<SearchResponse>){
        call.enqueue(object: Callback<SearchResponse> {
            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                _newsFeed.value = Pair(t, null)
            }

            override fun onResponse(
                call: Call<SearchResponse>,
                response: Response<SearchResponse>
            ) {
                val body = response.body()
                val docs = body?.response?.docs
                if (docs != null) {
                    _newsFeed.value = Pair(null, docs)
                }
            }
        })
    }


}

interface IRepository {
    fun getNewsFeed(): MutableLiveData<ArticlesOrError>
    fun getTopStories()
    fun getArts()
    fun getRealEstate()
    fun getAutomobiles()
    fun getTechnology()
    fun getMostPopular()
    fun getSearch(term: String?, beginDate: DateTime?, endDate: DateTime?, filters: List<String>?)
}