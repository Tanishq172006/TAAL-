package com.example.myapplication


internal expect object NativeBridge {
    fun playAsset(assetManager: Any, fileName: String)
}
