package com.example.mynews.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mynews.repositories.IRepository
import com.example.mynews.repositories.NotificationFrontendRepo

class NotificationsViewModel(val repo: NotificationFrontendRepo) : ViewModel() {

    private val notificationFilters = MutableLiveData<List<String>>()
    private val notificationQuery = MutableLiveData<String>()

    fun setNotificationsActive(active: Boolean) {
        if (active) {
            repo.setNotificationsActive()
        } else {
            repo.setNotificationsInactive()
        }
    }

    fun getNotificationsActive(): LiveData<Boolean> {
        return repo.getNotificationsActive()
    }

    fun setNotificationQuery(q: String) {
        repo.setNotificationQuery(q)
    }

    fun getNotificationQuery(): LiveData<String> {
        return repo.getNotificationQuery()
    }

    fun setNotificationFilters(f: List<String>) {
        repo.setNotificationFilters(f)
    }

    fun getNotificationFilters(): LiveData<List<String>> {
        return repo.getNotificationFilters()
    }
}
