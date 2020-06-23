package com.example.mynews.di

import com.example.mynews.notifications.Notifier
import com.example.mynews.repositories.*
import com.example.mynews.viewmodels.MainViewModel
import com.example.mynews.viewmodels.NotificationsViewModel
import com.example.mynews.viewmodels.SearchViewModel
import org.koin.dsl.module

val newsModule = module {
    single { MainViewModel(get()) }  // this needs to be a singleton to share across fragments.
    single { NotificationsViewModel(get()) }
    single { SearchViewModel(get()) }

    single { Repository( get(), get()) as IRepository }
    single { NotificationBackendRepo(get()) }
    single { NotificationFrontendRepo(get()) }
    single { TrackedSearchHistoryRepo(get()) }

    single { Notifier(get()) }

}