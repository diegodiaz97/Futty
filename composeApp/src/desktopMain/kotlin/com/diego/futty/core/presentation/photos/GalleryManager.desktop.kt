package com.diego.futty.core.presentation.photos

import androidx.compose.runtime.Composable

actual class GalleryManager actual constructor(onLaunch: () -> Unit) {
    actual fun launch() {
    }
}

@Composable
actual fun rememberGalleryManager(onResult: (SharedImage?) -> Unit): GalleryManager {
    TODO("Not yet implemented")
}