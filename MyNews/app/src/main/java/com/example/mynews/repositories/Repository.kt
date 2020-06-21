package com.example.mynews.repositories

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.mynews.*
import com.example.mynews.api.formatDateForQuery
import com.example.mynews.api.formatNewsDeskFilters
import com.example.mynews.api.timesService
import com.example.mynews.models.search.TrackedSearchAlertRequest
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

    private val _newsFeed = MutableLiveData<ArticlesOrError>()
    private val prefs = appContext.getSharedPreferences(PREFERENCE_FILE, Context.MODE_PRIVATE)
    private val _articlesLoading = MutableLiveData<Boolean>()
    private val _notificationQuery = MutableLiveData<String>()
    private val _notificationFilters = MutableLiveData<List<String>>()
    private val _notificationActive= MutableLiveData<Boolean>()

    override fun getNewsFeed(): MutableLiveData<ArticlesOrError> { return _newsFeed }

    override fun articlesLoading(): MutableLiveData<Boolean> { return _articlesLoading }

    override fun getTopStories(){ loadFromTopStories(timesService.topStories(apiKey)) }

    override fun getArts() { loadFromTopStories(timesService.arts(apiKey)) }

    override fun getRealEstate() { loadFromTopStories(timesService.realEstate(apiKey)) }

    override fun getAutomobiles(){ loadFromTopStories(timesService.automobiles(apiKey)) }

    override fun getTechnology() { loadFromTopStories(timesService.technology(apiKey)) }

    override fun getMostPopular(){ loadFromMostPopular(timesService.mostPopular(apiKey)) }

    override fun getSearch(query: String?, beginDate:DateTime?, endDate:DateTime?,
                           newsDesks: List<String>?, fromBackgroundProcess:Boolean)
    {

        val tracking = TrackedSearchAlertRequest(newsDesks, query,
            null, beginDate, endDate)

        val call =timesService.search(
            query=query,
            apiKey = apiKey,
            filters = formatNewsDeskFilters(newsDesks),
            beginDate = formatDateForQuery(beginDate),
            endDate = formatDateForQuery(endDate)
        )

        loadFromSearch(call, tracking, fromBackgroundProcess)
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

    private fun loadFromSearch(call: Call<SearchResponse>, tracking: TrackedSearchAlertRequest,
                               fromBackgroundProcess: Boolean){
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
                    tracking.searchResult = docs
                    if (fromBackgroundProcess) {
                        checkAndNotify(tracking)
                    } else {
                        _newsFeed.value = Pair(null, docs)
                    }
                    save(tracking)

                }
                _articlesLoading.value = false
            }
        })
    }

    private fun save(searchAlertRequest: TrackedSearchAlertRequest) {
        val newHistory = fetchSearchAlertHistory()
            .union(listOf(searchAlertRequest.hashCode())).joinToString(",")
        prefs
            .edit()
            .putString(SEARCH_ALERT_STORAGE_KEY, newHistory)
            .apply()
    }

    private fun searchAlertHistoryContains(searchAlertRequest: TrackedSearchAlertRequest): Boolean {
        return fetchSearchAlertHistory().contains(searchAlertRequest.hashCode())
    }

    private fun fetchSearchAlertHistory(): Set<Int> {
        val serialized = prefs.getString(SEARCH_ALERT_STORAGE_KEY, "")
        val deserializedStep1:List<String> = serialized!!.split(",").filter{ !it.isEmpty() }
        val deserialezedStep2:List<Int> = deserializedStep1.map{it.toInt()}
        return deserialezedStep2.toSet()
    }

    private fun checkAndNotify(tracking: TrackedSearchAlertRequest) {
        if (!searchAlertHistoryContains(tracking)) {
            notifier.notify(tracking)
        }
    }

    private fun updateNotifications() {

        val query = _notificationQuery.value
        val filters = _notificationFilters.value
        val active = _notificationActive.value


//        TODO MTW
//        if (active !=null && active) {
//            WorkManager.getInstance(appContext).cancelAllWorkByTag(NOTIFICATION_TAG)
//
//            val workRequest = PeriodicWorkRequestBuilder<NotificationWorker>(
//                com.example.mynews.NOTIFICATION_PERIOD_IN_MINUTES,
//                TimeUnit.MINUTES
//            ).build()
//
//            WorkManager.getInstance(appContext).enqueue(workRequest)
//            prefs.edit().putBoolean(NOTIFICATION_ACTIVE_PREF, true).apply()
//        } else {
//            WorkManager.getInstance(appContext).cancelAllWorkByTag(NOTIFICATION_TAG)
//        }

    }

    override fun setNotificationsActive() {
        prefs.edit().putBoolean(NOTIFICATION_ACTIVE_PREF, true).apply()
        _notificationActive.value = true
        updateNotifications()
    }

    override fun setNotificationsInactive(
    ) {
        prefs.edit().putBoolean(NOTIFICATION_ACTIVE_PREF, false).apply()
        _notificationActive.value = false
        updateNotifications()
    }

    override fun setNotificationQuery(q: String) {
        prefs.edit().putString(NOTIFICATION_QUERY_PREF, q).apply()
        _notificationQuery.value = q
        updateNotifications()
    }

    override fun setNotificationFilters(f: List<String>) {
        prefs.edit().putString(NOTIFICATION_FILTERS_PREF, f.joinToString(",") ).apply()
        _notificationFilters.value = f
       updateNotifications()
    }

    override fun getNotificationQuery(): MutableLiveData<String> {
        return _notificationQuery
    }

    override fun getNotificationFilters(): MutableLiveData<List<String>> {
        return _notificationFilters
    }

    override fun getNotificationsActive():MutableLiveData<Boolean> {
        return _notificationActive
    }

    init {
        _notificationFilters.value = prefs
            .getString(NOTIFICATION_FILTERS_PREF, "")!!
            .split(",")
        _notificationQuery.value = prefs.getString(NOTIFICATION_QUERY_PREF, "")
        _notificationActive.value = prefs.getBoolean(NOTIFICATION_ACTIVE_PREF, false)
        updateNotifications()
    }

}
