package com.itachi.explore

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import me.myatminsoe.mdetect.MDetect

@HiltAndroidApp
class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        MDetect.init(this@MyApplication)

    }
}