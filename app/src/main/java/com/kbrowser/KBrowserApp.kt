package com.kbrowser

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class KBrowserApp : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}
