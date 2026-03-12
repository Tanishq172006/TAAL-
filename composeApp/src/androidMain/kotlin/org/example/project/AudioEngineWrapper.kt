package org.example.project

import android.content.Context
import android.content.res.AssetManager

class AudioEngineWrapper(context: Context) {
    private val appContext = context.applicationContext
    init {
        System.loadLibrary("audioengine")
    }

//    external fun playAsset(assetManager: AssetManager, fileName: String)
// TODO: Make a proper wrapper for all platforms
}
