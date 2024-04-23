package com.example.worldwanderer.application

import android.app.Application
import com.google.firebase.FirebaseApp

class WorldWandererApplication : Application() {

        override fun onCreate() {
            super.onCreate()
            FirebaseApp.initializeApp(this)
        }

}