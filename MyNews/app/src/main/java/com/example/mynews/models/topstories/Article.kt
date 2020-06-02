package com.example.mynews.models.topstories

import com.example.mynews.models.AbstractArticle


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

    @get:JvmName("getSection_")
    val section: String,

    val short_url: String,
    val subsection: String,

    @get:JvmName("getTitle_")
    val title: String,

    val updated_date: String,

    @get:JvmName("getUri_")
    val uri: String,

    val url: String
): AbstractArticle() {

    override fun getTitle():String { return title }
    override fun getSection(): String { return section }

    override fun getThumbnailUrl():String? {
        var url:String? =  null
        try {
            url = multimedia.filter {
                it.format == "Standard Thumbnail"
            }.getOrNull(0)?.url
        } catch (e: Exception) {}

        return url
    }

    override fun getPublishedDate(): String {
        return published_date
    }

    override fun getUri() : String { return uri }

}

