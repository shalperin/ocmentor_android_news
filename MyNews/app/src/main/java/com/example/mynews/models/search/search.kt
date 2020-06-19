package com.example.mynews.models.search

import com.example.mynews.models.AbstractNewsDoc
import com.example.mynews.staticBasePath


data class Response(
    val copyright: String,
    val response: ResponseX,
    val status: String
)

data class ResponseX(
    val docs: List<Doc>,
    val meta: Meta
)

data class Doc(
    val _id: String?,
    val `abstract`: String?,
    val byline: Byline?,
    val document_type: String?,
    val headline: Headline?,
    val keywords: List<Keyword>?,
    val lead_paragraph: String?,
    val multimedia: List<Multimedia>?,
    val news_desk: String?,
    val print_page: String?,
    val print_section: String?,
    val pub_date: String,
    val section_name: String?,
    val snippet: String?,
    val source: String?,
    val subsection_name: String?,
    val type_of_material: String?,

    @get:JvmName("getUri_")
    val uri: String,
    val web_url: String?,
    val word_count: Int?
): AbstractNewsDoc() {
    override fun getThumbnailUrl(): String? {
        val path = multimedia
            ?.filter { it.subtype == "thumbnail" }
            ?.getOrNull(0)
            ?.url

        return relStatic(path)
    }

    override fun getSectionOrType(): String {
        if (section_name != null && !section_name.isEmpty()) {
            return section_name
        } else if (document_type != null && !document_type.isEmpty()) {
            return document_type
        } else {
            return ""
        }
    }

    override fun getTitle(): String {
        return headline?.main ?: ""
    }

    override fun getUri(): String {
        return uri
    }

    override fun getPublishedDate(): String {
        return pub_date
    }

}

data class Meta(
    val hits: Int,
    val offset: Int,
    val time: Int
)

data class Byline(
    val organization: Any,
    val original: String,
    val person: List<Person>
)

data class Headline(
    val content_kicker: Any,
    val kicker: Any,
    val main: String,
    val name: Any,
    val print_headline: String,
    val seo: Any,
    val sub: Any
)

data class Keyword(
    val major: String,
    val name: String,
    val rank: Int,
    val value: String
)

data class Multimedia(
    val caption: Any,
    val credit: Any,
    val crop_name: String,
    val height: Int,
    val legacy: Legacy,
    val rank: Int,
    val subType: String,
    val subtype: String,
    val type: String,
    val url: String,
    val width: Int
)

data class Person(
    val firstname: String,
    val lastname: String,
    val middlename: Any,
    val organization: String,
    val qualifier: Any,
    val rank: Int,
    val role: String,
    val title: Any
)

data class Legacy(
    val thumbnail: String,
    val thumbnailheight: Int,
    val thumbnailwidth: Int,
    val wide: String,
    val wideheight: Int,
    val widewidth: Int,
    val xlarge: String,
    val xlargeheight: Int,
    val xlargewidth: Int
)


fun relStatic(path: String?) : String? {
    if (path != null) {
        return staticBasePath + path
    } else {
        return null
    }
}