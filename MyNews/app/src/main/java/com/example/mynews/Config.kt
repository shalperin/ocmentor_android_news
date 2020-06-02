package com.example.mynews

import com.example.mynews.models.AbstractArticle
import okhttp3.logging.HttpLoggingInterceptor

typealias ArticlesOrError = Pair<Throwable?, List<AbstractArticle>?>

const val apiKey = BuildConfig.API_KEY
const val apiSecret = BuildConfig.API_SECRET
const val appId = BuildConfig.APP_ID

val logLevel = HttpLoggingInterceptor.Level.BODY

const val baseUrl = "https://api.nytimes.com/svc/"
const val topStoriesPath = "topstories/v2/home.json"
const val mostPopularPath = "mostpopular/v2/emailed/7.json"
const val artsPath = "topstories/v2/arts.json"
const val realEstatePath = "topstories/v2/realestate.json"
const val technologyPath = "topstories/v2/technology.json"
const val automobilesPath = "topstories/v2/automobiles.json"


