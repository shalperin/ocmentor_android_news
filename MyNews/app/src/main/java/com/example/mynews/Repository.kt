package com.example.mynews

import androidx.lifecycle.MutableLiveData
import com.example.mynews.models.topstories.Response
import retrofit2.Call
import retrofit2.Callback

class Repository :IRepository {

    private val newsFeed = MutableLiveData<ArticlesOrError>()

    override fun getTopStories():MutableLiveData<ArticlesOrError> {
        return loadFromTopStories(timesService.topStories(apiKey))
    }

    override fun getArts():MutableLiveData<ArticlesOrError> {
        return loadFromTopStories(timesService.arts(apiKey))
    }

    override fun getRealEstate():MutableLiveData<ArticlesOrError> {
        return loadFromTopStories(timesService.realEstate(apiKey))
    }

    override fun getAutomobiles():MutableLiveData<ArticlesOrError> {
        return loadFromTopStories(timesService.automobiles(apiKey))
    }

    override fun getTechnology():MutableLiveData<ArticlesOrError> {
        return loadFromTopStories(timesService.technology(apiKey))
    }

    override fun getMostPopular():MutableLiveData<ArticlesOrError> {
        return loadFromMostPopular(timesService.mostPopular(apiKey))
    }

    override fun initNewsFeed() = getTopStories()

    fun loadFromTopStories(call: Call<Response>): MutableLiveData<ArticlesOrError> {
        call.enqueue(object: Callback<Response>{
            override fun onFailure(call: Call<Response>, t: Throwable) {
                newsFeed.value = Pair(t, null)
            }

            override fun onResponse(call: Call<Response>, response: retrofit2.Response<Response>) {
                val response = response.body()
                if (response != null) {
                    newsFeed.value = Pair(null, response.results)
                }
            }
        })
        return newsFeed
    }

    fun loadFromMostPopular(call: Call<com.example.mynews.models.mostpopular.Response>):
            MutableLiveData<ArticlesOrError> {
        call.enqueue(object: Callback<com.example.mynews.models.mostpopular.Response>{
            override fun onFailure(call: Call<com.example.mynews.models.mostpopular.Response>, t: Throwable) {
                newsFeed.value = Pair(t, null)
            }

            override fun onResponse(call: Call<com.example.mynews.models.mostpopular.Response>,
                                    response: retrofit2.Response<com.example.mynews.models.mostpopular.Response>) {
                val response = response.body()
                if (response != null) {
                    newsFeed.value = Pair(null, response.results)
                }
            }
        })
        return newsFeed
    }


}

interface IRepository {
    fun initNewsFeed(): MutableLiveData<ArticlesOrError>
    fun getTopStories() : MutableLiveData<ArticlesOrError>
    fun getArts():MutableLiveData<ArticlesOrError>
    fun getRealEstate():MutableLiveData<ArticlesOrError>
    fun getAutomobiles():MutableLiveData<ArticlesOrError>
    fun getTechnology():MutableLiveData<ArticlesOrError>
    fun getMostPopular():MutableLiveData<ArticlesOrError>

}