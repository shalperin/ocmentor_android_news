package com.example.mynews.di

import com.example.mynews.IRepository
import com.example.mynews.Repository
import com.example.mynews.viewmodels.MainViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val newsModule = module {
    viewModel { MainViewModel(get()) }
    single { Repository() as IRepository }
}