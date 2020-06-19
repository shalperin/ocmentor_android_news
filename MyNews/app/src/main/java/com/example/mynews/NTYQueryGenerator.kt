package com.example.mynews

import org.joda.time.DateTime

fun formatNewsDeskFilters(desks: List<String>?): String? {
    if (desks == null || desks.size == 0) {
        return null
    } else {
        var sb = StringBuilder()
        sb.append("news_desk:(")
        sb.append(
            desks.map { "\"" + it + "\""}
                .joinToString(separator=" "))
        sb.append(")")
        return sb.toString()
    }
}

fun formatDateForQuery(dt: DateTime?) : String? {
    if (dt == null) {
        return null
    } else {
        return dt.toString("YYYYMMdd")
    }
}
