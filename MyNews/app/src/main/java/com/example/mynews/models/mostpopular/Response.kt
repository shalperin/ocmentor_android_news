package com.example.mynews.models.mostpopular

data class Response(
    val copyright: String,
    val num_results: Int,
    val results: List<Article>,
    val status: String
)
