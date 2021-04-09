package ru.android.easyscreenshots

import android.graphics.Bitmap
import java.io.File

interface MainContract : Screenshot

interface Screenshot {
    fun onScreenshot(title: String, file: File, bitmap: Bitmap)
}