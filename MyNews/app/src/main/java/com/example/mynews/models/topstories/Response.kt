package com.example.mynews.models.topstories

import com.example.mynews.models.topstories.Article

data class Response(
    val copyright: String,
    val last_updated: String,
    val num_results: Int,
    val results: List<Article>,
    val section: String,
    val status: String
)