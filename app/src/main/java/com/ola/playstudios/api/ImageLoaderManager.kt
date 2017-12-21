package com.ola.playstudios.api

import android.content.Context
import com.android.volley.toolbox.Volley
import android.graphics.Bitmap
import android.support.v4.util.LruCache
import com.android.volley.toolbox.ImageLoader


/**
 * Created by pankaj on 21/12/17.
 */
object ImageLoaderManager {

    fun getImageLoader(context: Context?): ImageLoader {

        val mRequestQueue = Volley.newRequestQueue(context)
        return ImageLoader(
                mRequestQueue,
                object : ImageLoader.ImageCache {
                    private val lruCache = object : LruCache<String, Bitmap>(calculateMaxByteSize(context)) {
                        override fun sizeOf(url: String, bitmap: Bitmap): Int {
                            return bitmap.byteCount
                        }
                    }

                    @Synchronized override fun getBitmap(url: String): Bitmap? {
                        return lruCache.get(url)
                    }

                    @Synchronized override fun putBitmap(url: String, bitmap: Bitmap) {
                        lruCache.put(url, bitmap)
                    }
                })
    }

    private fun calculateMaxByteSize(context: Context?): Int {
        context?.let {
            val displayMetrics = context.resources.displayMetrics
            val screenBytes = displayMetrics.widthPixels * displayMetrics.heightPixels * 4
            return screenBytes * 3
        }
        return 0
    }
}