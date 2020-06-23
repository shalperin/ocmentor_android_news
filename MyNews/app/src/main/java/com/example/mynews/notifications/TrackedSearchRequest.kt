package com.example.mynews.notifications

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.example.mynews.PREFERENCE_FILE
import com.example.mynews.SEARCH_ALERT_STORAGE_KEY
import com.example.mynews.models.AbstractNewsDoc
import org.joda.time.DateTime

data class TrackedSearchAlertRequest(
    val newsDesks: Set<String>?,
    val query: String?,
    var searchResult:List<AbstractNewsDoc>?,
    val beginDate: DateTime?,
    val endDate: DateTime?
    ) {

    val TAG = this::class.java.simpleName

    override fun hashCode() : Int {
        var hashCode = newsDesks.hashCode()
        + query.hashCode()
        + beginDate.hashCode()
        + endDate.hashCode()

        if (searchResult != null) {
            hashCode += searchResult!!.map { it.hashCode() }.sum()
        }

        return hashCode
    }
}