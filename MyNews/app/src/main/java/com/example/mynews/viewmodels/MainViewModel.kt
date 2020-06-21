package com.example.mynews.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mynews.ArticlesOrError
import com.example.mynews.FOREGROUND
import com.example.mynews.repositories.IRepository
import org.joda.time.DateTime


class MainViewModel(private val repo: IRepository) : ViewModel() {
    private val newsFeed: LiveData<ArticlesOrError>
    private val currentArticleUrl = MutableLiveData<String>()

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








}