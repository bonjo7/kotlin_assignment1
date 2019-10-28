package main

import android.app.Application
import models.RingfortJSONStore
import models.RingfortStore
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

class MainApp : Application(), AnkoLogger {

    lateinit var ringforts : RingfortStore

    override fun onCreate() {
        super.onCreate()
        ringforts = RingfortJSONStore(applicationContext)
        info("Archaeological Field Work App Started")
    }
}

