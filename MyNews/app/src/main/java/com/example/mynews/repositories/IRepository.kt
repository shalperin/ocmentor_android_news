package com.example.mynews.repositories

import androidx.lifecycle.MutableLiveData
import com.example.mynews.ArticlesOrError
import org.joda.time.DateTime


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
        newsDesks: List<String>?,
        fromBackgroundProcess: Boolean)

    fun setNotificationsActive()
    fun setNotificationsInactive()
    fun setNotificationQuery(q: String)
    fun setNotificationFilters(f: List<String>)
    fun getNotificationQuery(): MutableLiveData<String>
    fun getNotificationFilters(): MutableLiveData<List<String>>
    fun getNotificationsActive():MutableLiveData<Boolean>
}