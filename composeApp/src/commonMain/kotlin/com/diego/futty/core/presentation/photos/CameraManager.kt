package com.diego.futty.core.presentation.photos

import androidx.compose.runtime.Composable

@Composable
expect fun rememberCameraManager(onResult: (SharedImage?) -> Unit): CameraManager

expect class CameraManager(
    onLaunch: () -> Unit
) {
    fun launch()
}
