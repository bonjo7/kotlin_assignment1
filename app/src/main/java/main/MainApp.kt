package main

import android.app.Application
import models.RingfortMemStore
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

class MainApp : Application(), AnkoLogger {

    val ringforts = RingfortMemStore()

    override fun onCreate() {
        super.onCreate()
        info("Archaeological Field Work App Started")
    }
}

