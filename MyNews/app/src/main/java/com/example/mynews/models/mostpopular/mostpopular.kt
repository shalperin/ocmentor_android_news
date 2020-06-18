package com.example.mynews.models.mostpopular

import com.example.mynews.models.AbstractArticle

data class Response(
    val copyright: String,
    val num_results: Int,
    val results: List<Article>,
    val status: String
)

data class Article(
    val `abstract`: String,
    val adx_keywords: String,
    val asset_id: Long,
    val byline: String,
    val column: Any,
    val des_facet: List<String>,
    val eta_id: Int,
    val geo_facet: List<String>,
    val id: Long,
    val media: List<Media>,
    val nytdsection: String,
    val org_facet: List<String>,
    val per_facet: List<String>,
    val published_date: String,

    @get:JvmName("getSection_")
    val section: String,

    val source: String,
    val subsection: String,

    @get:JvmName("getTitle_")
    val title: String,
    val type: String,
    val updated: String,

    @get:JvmName("getUri_")
    val uri: String,
    val url: String
) : AbstractArticle() {

    override fun getTitle(): String { return title }

    override fun getSection():String { return section }

    override fun getPublishedDate(): String { return published_date}

    override fun getThumbnailUrl() : String? {
        val url = media
            .getOrNull(0)
            ?.`media-metadata`
            ?.filter{ it.format == "Standard Thumbnail" }
            ?.getOrNull(0)?.url
        return url
    }

    override fun getUri() : String { return uri }
}


data class Media(
    val approved_for_syndication: Int,
    val caption: String,
    val copyright: String,
    val `media-metadata`: List<MediaMetadata>,
    val subtype: String,
    val type: String
)



data class MediaMetadata(
    val format: String,
    val height: Int,
    val url: String,
    val width: Int
)