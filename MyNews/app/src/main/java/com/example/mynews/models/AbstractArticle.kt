package com.example.mynews.models

import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat

abstract class AbstractArticle {
    abstract fun getThumbnailUrl() : String?
    abstract fun getSection() : String
    abstract fun getTitle() : String
    abstract fun getUri(): String
    abstract fun getPublishedDate(): String

    override fun equals(other: Any?): Boolean {
        return this.hashCode() == other.hashCode()
    }

    override fun hashCode(): Int {
        return getThumbnailUrl().hashCode() * getSection().hashCode() * getTitle().hashCode() *
                getUri().hashCode() * getPublishedDate().hashCode()
    }

    fun getHumanizedPublishedDate(): String {
        val dt = DateTime.parse(getPublishedDate())
        val formatterOut = DateTimeFormat.forPattern("M/d/yy")
        return dt.toString(formatterOut)
    }
}