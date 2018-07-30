package ru.yandex.moykoshelek

import android.app.Application
import com.androidnetworking.AndroidNetworking
import com.treebo.internetavailabilitychecker.InternetAvailabilityChecker


class MoyKoshelekApp : Application() {

    override fun onCreate() {
        super.onCreate()
        AndroidNetworking.initialize(applicationContext)
        InternetAvailabilityChecker.init(this)
        instance = this
    }

    companion object {
        @get:Synchronized
        var instance: MoyKoshelekApp? = null
            private set
    }
}