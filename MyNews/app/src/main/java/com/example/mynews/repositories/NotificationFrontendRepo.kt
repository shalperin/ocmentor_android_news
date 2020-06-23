package com.example.mynews.repositories

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.mynews.*
import com.example.mynews.notifications.NotificationWorker
import java.util.concurrent.TimeUnit

class NotificationFrontendRepo(val appContext: Context) {
    private val TAG = this::class.java.simpleName
    private val _notificationQuery = MutableLiveData<String>()
    private val _notificationFilters = MutableLiveData<List<String>>()
    private val _notificationActive= MutableLiveData<Boolean>()
    private val prefs = appContext.getSharedPreferences(PREFERENCE_FILE, Context.MODE_PRIVATE)

    private fun updateNotifications() {

        val query = _notificationQuery.value
        val filters = _notificationFilters.value
        val active = _notificationActive.value

        if (active !=null && active) {
            Log.d(TAG, "active: starting workmanager")
            WorkManager.getInstance(appContext).cancelAllWorkByTag(NOTIFICATION_TAG)

            val workRequest = PeriodicWorkRequestBuilder<NotificationWorker>(
                NOTIFICATION_PERIOD_IN_MINUTES,
                TimeUnit.MINUTES
            ).setInitialDelay(NOTIFICATION_INITIAL_DELAY, TimeUnit.MINUTES)
                .build()

            WorkManager.getInstance(appContext).enqueue(workRequest)
            prefs.edit().putBoolean(NOTIFICATION_ACTIVE_PREF, true).apply()
        } else {
            Log.d(TAG, "not active: cancelling all work")
            WorkManager.getInstance(appContext).cancelAllWorkByTag(NOTIFICATION_TAG)
        }
    }

    fun setNotificationsActive() {
        prefs.edit().putBoolean(NOTIFICATION_ACTIVE_PREF, true).apply()
        _notificationActive.value = true
        updateNotifications()
    }

    fun setNotificationsInactive(
    ) {
        prefs.edit().putBoolean(NOTIFICATION_ACTIVE_PREF, false).apply()
        _notificationActive.value = false
        updateNotifications()
    }

    fun setNotificationQuery(q: String) {
        prefs.edit().putString(NOTIFICATION_QUERY_PREF, q).apply()
        _notificationQuery.value = q
    }

    fun setNotificationFilters(f: List<String>) {
        prefs.edit().putString(NOTIFICATION_FILTERS_PREF, f.joinToString(",") ).apply()
        _notificationFilters.value = f
    }

    fun getNotificationQuery(): MutableLiveData<String> {
        return _notificationQuery
    }

    fun getNotificationFilters(): MutableLiveData<List<String>> {
        return _notificationFilters
    }

    fun getNotificationsActive(): MutableLiveData<Boolean> {
        return _notificationActive
    }

    init {
        Log.d(TAG, "repo init")
        _notificationFilters.value = prefs
            .getString(NOTIFICATION_FILTERS_PREF, "")!!
            .split(",")
        _notificationQuery.value = prefs.getString(NOTIFICATION_QUERY_PREF, "")
        _notificationActive.value = prefs.getBoolean(NOTIFICATION_ACTIVE_PREF, false)
        updateNotifications()
    }
}

