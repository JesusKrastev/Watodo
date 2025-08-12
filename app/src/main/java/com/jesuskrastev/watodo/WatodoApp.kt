package com.jesuskrastev.watodo

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class WatodoApp : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}