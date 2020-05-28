package com.example.mynews

import android.app.Application
import com.example.mynews.di.newsModule
import net.danlew.android.joda.JodaTimeAndroid
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainApplication : Application() {
    override fun onCreate(){
        super.onCreate()
        // start Koin!
        startKoin {
            // declare used Android context
            androidContext(this@MainApplication)
            // declare modules
            modules(newsModule)
        }

        JodaTimeAndroid.init(this);

    }
}