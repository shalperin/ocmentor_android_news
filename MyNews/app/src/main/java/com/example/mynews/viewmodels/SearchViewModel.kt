package com.example.mynews.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mynews.FOREGROUND
import com.example.mynews.repositories.IRepository
import org.joda.time.DateTime

class SearchViewModel(val repo: IRepository) : ViewModel() {
    private val searchBeginDate =  MutableLiveData<DateTime>()
    private val searchEndDate = MutableLiveData<DateTime>()
    private val searchFilters = MutableLiveData<List<String>>()
    private val searchQuery = MutableLiveData<String>()

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

    fun setSearchFilters(f: List<String>) {
        searchFilters.value = f
    }


    fun submitSearch() {
        val beginDate = searchBeginDate.value
        val endDate = searchEndDate.value
        val filters = searchFilters.value
        val query = searchQuery.value

        repo.getSearch(query, beginDate, endDate, filters,
            FOREGROUND
        )
    }

    fun getSearchQuery(): LiveData<String> {
        return searchQuery
    }

    fun getFilters(): LiveData<List<String>> {
        return searchFilters
    }
}