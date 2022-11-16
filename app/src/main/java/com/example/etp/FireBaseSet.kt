package com.example.etp

import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class FireBaseSet:android.app.Application() {
    override fun onCreate() {
        super.onCreate()
        Firebase.database.setPersistenceEnabled(true)
    }
}