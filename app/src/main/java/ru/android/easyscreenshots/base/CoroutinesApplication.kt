package ru.android.easyscreenshots.base

import android.app.Application

class CoroutinesApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        SimpleScreenshot.init(this)
    }

}