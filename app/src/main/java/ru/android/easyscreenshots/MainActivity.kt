package ru.android.easyscreenshots

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import java.io.File
import java.io.FileOutputStream

class MainActivity : AppCompatActivity(), MainContract {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onScreenshot(title: String, file: File, bitmap: Bitmap) {
        val fileOutputStream = FileOutputStream(file)
        val share = Intent(Intent.ACTION_SEND)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream)
        fileOutputStream.close()
        share.type = "image/jpg"
        val photoURI = FileProvider.getUriForFile(
            applicationContext,
            "${BuildConfig.APPLICATION_ID}.fileprovider",
            file
        )
        share.putExtra(Intent.EXTRA_STREAM, photoURI)
        startActivity(Intent.createChooser(share, title))
    }

}