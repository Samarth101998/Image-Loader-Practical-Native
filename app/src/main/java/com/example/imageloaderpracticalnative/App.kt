package com.example.imageloaderpracticalnative

import android.app.Application
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache
import com.nostra13.universalimageloader.core.DisplayImageOptions
import com.nostra13.universalimageloader.core.ImageLoader
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration
import com.nostra13.universalimageloader.core.assist.ImageScaleType
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        val  config = ImageLoaderConfiguration.Builder(applicationContext)
            .memoryCache(WeakMemoryCache())
            .diskCacheSize(100 * 1024*1024)
            .build()

        ImageLoader.getInstance().init(config)
    }
}