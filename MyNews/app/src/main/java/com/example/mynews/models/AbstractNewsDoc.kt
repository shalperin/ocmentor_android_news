package com.example.mynews.models

import com.example.mynews.staticBasePath
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat

abstract class AbstractNewsDoc {
    abstract fun getThumbnailUrl() : String?
    abstract fun getSectionOrType() : String
    abstract fun getTitle() : String
    abstract fun getUri(): String
    abstract fun getPublishedDate(): String

    override fun equals(other: Any?): Boolean {
        return this.hashCode() == other.hashCode()
    }

    override fun hashCode(): Int {
        return getThumbnailUrl().hashCode() * getSectionOrType().hashCode() * getTitle().hashCode() *
                getUri().hashCode() * getPublishedDate().hashCode()
    }

    fun getHumanizedPublishedDate(): String {
        val dt = DateTime.parse(getPublishedDate())
        val formatterOut = DateTimeFormat.forPattern("M/d/yy")
        return dt.toString(formatterOut)
    }


}