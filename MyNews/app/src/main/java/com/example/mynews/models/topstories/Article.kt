package com.example.mynews.models.topstories

import org.joda.time.DateTime

import org.joda.time.format.DateTimeFormat


data class Article(
    val `abstract`: String,
    val byline: String,
    val created_date: String,
    val des_facet: List<String>,
    val geo_facet: List<String>,
    val item_type: String,
    val kicker: String,
    val material_type_facet: String,
    val multimedia: List<Multimedia>,
    val org_facet: List<String>,
    val per_facet: List<String>,
    val published_date: String,
    val section: String,
    val short_url: String,
    val subsection: String,
    val title: String,
    val updated_date: String,
    val uri: String,
    val url: String
) {

    //TODO the section "Most Popular" uses a slightly different API.  It looks OK with
    // this implementation, but images will be null.
    fun getStandardThumb():String? {
        var uri:String? =  null
        try {
            uri = multimedia.filter {
                it.format == "Standard Thumbnail"
            }.getOrNull(0)?.url
        } catch (e: Exception) {}

        return uri
    }

    fun getHumanizedPublishedDate(): String {
        val dt = DateTime.parse(published_date)
        val formatterOut = DateTimeFormat.forPattern("M/d/yy")
        return dt.toString(formatterOut)
    }
}

