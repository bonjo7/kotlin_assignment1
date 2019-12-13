package main

import android.app.Application
import models.*
import models.json.RingfortJSONStore
import models.room.RingfortStoreRoom
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

class MainApp : Application(), AnkoLogger {

    lateinit var users : UserStore

    lateinit var ringforts : RingfortStore

    lateinit var currentUser: UserModel

    override fun onCreate() {
        super.onCreate()
//        ringforts = RingfortStoreRoom(applicationContext)
//        users = UserJSONStore(applicationContext)
        ringforts= RingfortFireStore(applicationContext)
        info("Archaeological Field Work App Started")
    }
}

