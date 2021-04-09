package ru.android.easyscreenshots.base

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.os.Environment
import android.util.LruCache
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import java.io.File

@SuppressLint("StaticFieldLeak")
object SimpleScreenshot {

    var context: Context? = null

    fun init(applicationContext: Context?) {
        context = applicationContext
    }

    fun mergeMultiple(bitmapOne: Bitmap, bitmapTwo: Bitmap?): Bitmap? {
        val overlay = Bitmap.createBitmap(
            bitmapOne.width,
            bitmapOne.height + (bitmapTwo?.height ?: 0),
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(overlay)
        val paint = Paint()
        canvas.drawBitmap(bitmapOne, Matrix(), paint)
        if (bitmapTwo != null) {
            canvas.drawBitmap(bitmapTwo, 0f, bitmapOne.height.toFloat(), paint)
        }
        return overlay
    }

    fun getScreenBitmapFromView(view: View): Bitmap {
        val bitmapOne =
            Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmapOne)
        canvas.drawColor(Color.WHITE)
        view.draw(canvas)
        return bitmapOne
    }

    fun getScreenshotFromRecyclerView(view: RecyclerView): Bitmap? {
        val adapter = view.adapter
        var bigBitmap: Bitmap? = null
        if (adapter != null) {
            val size = adapter.itemCount
            var height = 0
            val paint = Paint()
            var iHeight = 0
            val maxMemory = (Runtime.getRuntime().maxMemory() / 1024).toInt()
            val cacheSize = maxMemory / 8
            val bitmapCache: LruCache<String, Bitmap> = LruCache(cacheSize)
            for (i in 0 until size) {
                val holder = adapter.createViewHolder(view, adapter.getItemViewType(i))
                adapter.onBindViewHolder(holder, i)
                holder.itemView.measure(
                    View.MeasureSpec.makeMeasureSpec(view.width, View.MeasureSpec.EXACTLY),
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
                )
                holder.itemView.layout(
                    0,
                    0,
                    holder.itemView.measuredWidth,
                    holder.itemView.measuredHeight
                )
                holder.itemView.isDrawingCacheEnabled = true
                holder.itemView.buildDrawingCache()
                val drawingCache = holder.itemView.drawingCache
                if (drawingCache != null) {
                    bitmapCache.put(i.toString(), drawingCache)
                }
                height += holder.itemView.measuredHeight
            }
            bigBitmap = Bitmap.createBitmap(view.measuredWidth, height, Bitmap.Config.ARGB_8888)
            val bigCanvas = Canvas(bigBitmap)
            bigCanvas.drawColor(Color.WHITE)
            for (i in 0 until size) {
                val bitmap: Bitmap = bitmapCache.get(i.toString())
                bigCanvas.drawBitmap(bitmap, 0f, iHeight.toFloat(), paint)
                iHeight += bitmap.height
                bitmap.recycle()
            }
        }
        return bigBitmap
    }

    fun createScreenImageFile(prefix: String, suffix: String): File {
        val directory = context?.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            prefix,
            suffix,
            directory
        )
    }

}