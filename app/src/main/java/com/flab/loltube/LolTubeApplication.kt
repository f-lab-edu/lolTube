package com.flab.loltube

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class LolTubeApplication : Application() {

    override fun onCreate() {
        super.onCreate()
    }
}
