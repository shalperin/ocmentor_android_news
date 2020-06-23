package com.example.mynews.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mynews.ArticlesOrError
import com.example.mynews.FOREGROUND
import com.example.mynews.repositories.IRepository
import com.example.mynews.repositories.NotificationFrontendRepo
import org.joda.time.DateTime


class MainViewModel(private val repo: IRepository) : ViewModel() {
    private val newsFeed: LiveData<ArticlesOrError>
    private val currentArticleUrl = MutableLiveData<String>()

    private val searchBeginDate =  MutableLiveData<DateTime>()
    private val searchEndDate = MutableLiveData<DateTime>()
    private val searchFilters = MutableLiveData<Set<String>>()
    private val searchQuery = MutableLiveData<String>()


    init {
        newsFeed = repo.getNewsFeed()
        repo.getTopStories()
    }

    fun articlesLoading(): MutableLiveData<Boolean> { return repo.articlesLoading() }

    fun getNewsFeed() : LiveData<ArticlesOrError> { return newsFeed }

    fun getTopStories() { repo.getTopStories() }

    fun getMostPopular() { repo.getMostPopular() }

    fun getArts() { repo.getArts() }

    fun getRealEstate() {repo.getRealEstate()}

    fun getAutomobiles() { repo.getAutomobiles() }

    fun getTechnology() { repo.getTechnology() }

    fun setCurrentArticleUrl(url: String) { currentArticleUrl.value = url }

    fun getCurrentArticleUrl(): LiveData<String> { return currentArticleUrl }





    fun setSearchBeginDate(dt: DateTime?) { searchBeginDate.value = dt }

    fun setSearchEndDate(dt: DateTime?) { searchEndDate.value = dt }

    fun getSearchBeginDate(): LiveData<DateTime> {
        return searchBeginDate
    }

    fun getSearchEndDate(): LiveData<DateTime> {
        return searchEndDate
    }

    fun setSearchQuery(q: String) {
        searchQuery.value = q

    }

    fun setSearchFilters(f: Set<String>) {
        searchFilters.value = f
    }


    fun submitSearch() {
        val beginDate = searchBeginDate.value
        val endDate = searchEndDate.value
        val filters = searchFilters.value
        val query = searchQuery.value

        repo.getSearch(query, beginDate, endDate, filters)
    }

    fun getSearchQuery(): LiveData<String> {
        return searchQuery
    }

    fun getFilters(): LiveData<Set<String>> {
        return searchFilters
    }





}