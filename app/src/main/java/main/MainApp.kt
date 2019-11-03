package main

import android.app.Application
import models.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

class MainApp : Application(), AnkoLogger {

    lateinit var users : UserStore

    lateinit var ringforts : RingfortStore

    lateinit var loggedInUser : UserModel

    override fun onCreate() {
        super.onCreate()
        ringforts = RingfortJSONStore(applicationContext)
        users = UserJSONStore(applicationContext)
        info("Archaeological Field Work App Started")
    }
}

