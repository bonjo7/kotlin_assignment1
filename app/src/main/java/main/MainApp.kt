package main

import android.app.Application
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import models.*
import models.json.RingfortJSONStore
import models.room.RingfortStoreRoom
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

class MainApp : Application(), AnkoLogger {

    lateinit var users : UserStore

    lateinit var ringforts : RingfortStore

    lateinit var currentUser: UserModel

//    lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreate() {
        super.onCreate()
//        ringforts = RingfortStoreRoom(applicationContext)
//        users = UserJSONStore(applicationContext)
        ringforts= RingfortFireStore(applicationContext)
        info("Archaeological Field Work App Started")
    }
}

