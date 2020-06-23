package com.example.mynews.repositories

import android.content.Context
import android.util.Log
import com.example.mynews.NOTIFICATION_FILTERS_PREF
import com.example.mynews.NOTIFICATION_QUERY_PREF
import com.example.mynews.PREFERENCE_FILE
import com.example.mynews.api.formatNewsDeskFilters
import com.example.mynews.api.timesService
import com.example.mynews.apiKey
import com.example.mynews.notifications.TrackedSearchAlertRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

//The argument for the two Notification repos
// is that when repos are used on a background thread
// from a WorkManager, they can't write to LiveData.

class NotificationBackendRepo(
    val appContext: Context) {
    private val prefs = appContext.getSharedPreferences(PREFERENCE_FILE, Context.MODE_PRIVATE)
    private val TAG = this::class.java.simpleName


    fun getTracking(query: String, newsDesks:Set<String>, callback: (TrackedSearchAlertRequest) -> Unit)
    {
        val tracking = TrackedSearchAlertRequest(
            newsDesks, query,
            null, null, null
        )

        val call = timesService.search(
            query = query, apiKey = apiKey,
            filters = formatNewsDeskFilters(newsDesks), beginDate = null, endDate = null
        )

        call.enqueue(
            object : Callback<SearchResponse> {
                override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                    Log.w(TAG, t.message ?: "some exception without message")
                }

                override fun onResponse(
                    call: Call<SearchResponse>, response: Response<SearchResponse>
                ) {
                    val body = response.body()
                    val docs = body?.response?.docs
                    if (docs != null) {
                        tracking.searchResult = docs
                        callback(tracking)
                    }
                }
            }
        )
    }


   fun getQuery():String {
        return prefs.getString(NOTIFICATION_QUERY_PREF, "")!!
    }

   fun getFilters(): Set<String> {
        return prefs
            .getString(NOTIFICATION_FILTERS_PREF, "")!!
            .split(",").toSet()
    }

}