package com.example.mynews.di

import com.example.mynews.IRepository
import com.example.mynews.Repository
import com.example.mynews.MainViewModel
import org.koin.dsl.module

val newsModule = module {

    // WRONG:
    //  viewModel { MainViewModel(get()) }
    single { MainViewModel(get()) }  // this needs to be a singleton to share across fragments.


    single { Repository() as IRepository }
}