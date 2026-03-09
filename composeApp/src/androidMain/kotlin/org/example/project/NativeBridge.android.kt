package com.example.myapplication

internal actual object NativeBridge {
    actual external fun playAsset(assetManager: Any, fileName: String)
}