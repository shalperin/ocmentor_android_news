package com.example.mynews.models.search

import android.content.Context
import com.example.mynews.models.AbstractNewsDoc
import org.joda.time.DateTime

data class TrackedSearchAlertRequest(
    val newsDesks: List<String>?,
    val query: String?,
    var searchResult:List<AbstractNewsDoc>?,
    val beginDate: DateTime?,
    val endDate: DateTime?
    ) {

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