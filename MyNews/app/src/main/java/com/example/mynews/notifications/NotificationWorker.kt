package com.example.mynews.notifications

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.mynews.repositories.NotificationBackendRepo
import com.example.mynews.repositories.TrackedSearchHistoryRepo
import org.koin.core.KoinComponent
import org.koin.core.inject

// You can kind of think of this as part of an MVC, where Notifier, sending the notifications,
// is the frontend, the repos are the backend, and this Worker class is the controller.

class NotificationWorker(appContext: Context, workerParams: WorkerParameters) : Worker(appContext,
    workerParams), KoinComponent {
    private val TAG = this::class.java.simpleName

    override fun doWork(): Result {
        val notificationBackendRepo : NotificationBackendRepo by inject()
        val notifier: Notifier by inject()
        val trackedSearchHistoryRepo: TrackedSearchHistoryRepo by inject()


        Log.d(TAG, "beginning notification worker")

        val query:String = notificationBackendRepo.getQuery()
        val filters:Set<String> = notificationBackendRepo.getFilters()

        notificationBackendRepo.getTracking(query, filters,
            // note that this is on a different thread
            { tracking:TrackedSearchAlertRequest ->
                if (!trackedSearchHistoryRepo.historyContains(tracking)) {
                    notifier.notify(query, filters)
                    trackedSearchHistoryRepo.addToHistory(tracking)
                }
            }
        )

        Log.d(TAG, "end notification worker")

        return Result.success()
    }
}
