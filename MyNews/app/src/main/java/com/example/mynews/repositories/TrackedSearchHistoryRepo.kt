package com.example.mynews.repositories

import android.content.Context
import android.util.Log
import com.example.mynews.PREFERENCE_FILE
import com.example.mynews.SEARCH_ALERT_STORAGE_KEY
import com.example.mynews.notifications.TrackedSearchAlertRequest

class TrackedSearchHistoryRepo(val appContext: Context) {
    private val TAG = this::class.java.simpleName
    private val prefs = appContext.getSharedPreferences(PREFERENCE_FILE, Context.MODE_PRIVATE)

    fun addToHistory(trackedSearch: TrackedSearchAlertRequest) {
        Log.d(TAG, "saving request with hashcode: " + trackedSearch.hashCode())
        saveHistory(getHistory().union(listOf(trackedSearch.hashCode())))
    }

    fun historyContains(trackedSearch: TrackedSearchAlertRequest): Boolean {
        Log.d(TAG, "checking hashcode: " + trackedSearch.hashCode())
        return getHistory().contains(trackedSearch.hashCode())
    }

    private fun getHistory(): Set<Int> {
        val serialized = prefs.getString(SEARCH_ALERT_STORAGE_KEY, "")
        val deserializedStep1:List<String> = serialized!!.split(",").filter{ !it.isEmpty() }
        val deserialezedStep2:List<Int> = deserializedStep1.map{it.toInt()}
        return deserialezedStep2.toSet()
    }

    private fun saveHistory(history: Set<Int>) {
        prefs
            .edit()
            .putString(SEARCH_ALERT_STORAGE_KEY, history.joinToString (",") )
            .apply()
    }


}