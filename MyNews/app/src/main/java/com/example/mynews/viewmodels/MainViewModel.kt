package com.example.mynews.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.mynews.ArticlesOrError
import com.example.mynews.IRepository


class MainViewModel(private val repo: IRepository) : ViewModel() {
    val newsFeed: LiveData<ArticlesOrError>

    init {
        newsFeed = repo.initNewsFeed()
    }

    fun getTopStories() { repo.getTopStories() }
    fun getMostPopular() { repo.getMostPopular() }
    fun getArts() { repo.getArts() }
    fun getRealEstate() {repo.getRealEstate()}
    fun getAutomobiles() { repo.getAutomobiles() }
    fun getTechnology() { repo.getTechnology() }

}