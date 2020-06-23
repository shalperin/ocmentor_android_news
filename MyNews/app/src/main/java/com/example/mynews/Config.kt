package com.example.mynews

import com.example.mynews.models.AbstractNewsDoc
import okhttp3.logging.HttpLoggingInterceptor

typealias ArticlesOrError = Pair<Throwable?, List<AbstractNewsDoc>?>

const val apiKey = BuildConfig.API_KEY
const val apiSecret = BuildConfig.API_SECRET
const val appId = BuildConfig.APP_ID

val logLevel = HttpLoggingInterceptor.Level.BASIC

const val BASE_URL = "https://api.nytimes.com/svc/"
const val TOP_STORIES_PATH = "topstories/v2/home.json"
const val MOST_POPULAR_PATH = "mostpopular/v2/emailed/7.json"
const val ARTS_PATH = "topstories/v2/arts.json"
const val REAL_ESTATE_PATH = "topstories/v2/realestate.json"
const val TECHNOLOGY_PATH = "topstories/v2/technology.json"
const val AUTOMOBILES_PATH = "topstories/v2/automobiles.json"
const val SEARCH_PATH = "search/v2/articlesearch.json?"

const val STATIC_BASE_PATH = "https://static01.nyt.com/"

const val API_SORT_ORDER_RELEVANCE = "relevance"
const val API_SORT_ORDER_NEWEST = "newest"
const val API_SORT_ORDER_OLDEST = "oldest"

const val SEARCH_ALERT_STORAGE_KEY = "searchAlertRequestHistory"
const val PREFERENCE_FILE = "news_shared_prefs"

const val FOREGROUND = false
const val BACKGROUND = true

const val NOTIFICATION_TAG = "new_article_notifications"
const val NOTIFICATION_PERIOD_IN_MINUTES = 24*60L
const val NOTIFICATION_INITIAL_DELAY = 24*60L
const val NOTIFICATION_QUERY_PREF = "notification_query"
const val NOTIFICATION_FILTERS_PREF = "notification_filters"
const val NOTIFICATION_ACTIVE_PREF = "notification_active"

const val CHANNEL_ID = "new_articles"
const val CHANNEL_NAME = "New Articles Channel"
const val CHANNEL_DESCRIPTION = "A notification channel for new New York Times articles."
const val NOTIFICATION_TITLE = "There were new articles on the last background search."
const val NOTIFICATION_CONTENT = "tap to view"