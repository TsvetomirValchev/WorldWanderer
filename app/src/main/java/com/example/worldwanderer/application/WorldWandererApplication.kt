package com.example.worldwanderer.application

import android.app.Application
import com.google.firebase.FirebaseApp

class WorldWandererApplication : Application() {

        override fun onCreate() {
            super.onCreate()
            // Initialize Firebase when the application is created
            FirebaseApp.initializeApp(this)
        }

}