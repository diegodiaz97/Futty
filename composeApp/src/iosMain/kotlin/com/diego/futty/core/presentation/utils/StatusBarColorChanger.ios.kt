package com.diego.futty.core.presentation.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.useContents
import platform.CoreGraphics.CGRectMake
import platform.UIKit.UIApplication
import platform.UIKit.UIColor
import platform.UIKit.UIScreen
import platform.UIKit.UITabBar
import platform.UIKit.UIView
import platform.UIKit.UIWindow

@OptIn(ExperimentalForeignApi::class)
@Composable
private fun statusBarView() = remember {
    val keyWindow: UIWindow? =
        UIApplication.sharedApplication.windows.firstOrNull { (it as? UIWindow)?.isKeyWindow() == true } as? UIWindow
    val safeAreaInsets = UIApplication.sharedApplication.keyWindow?.safeAreaInsets
    val width = UIScreen.mainScreen.bounds.useContents { this.size.width }
    var topInsets = 0.0

    safeAreaInsets?.let {
        topInsets = safeAreaInsets.useContents {
            this.top
        }
    }
    val tag = 3848245L

    val statusBarView = UIView(frame = CGRectMake(0.0,0.0, width, topInsets))

    keyWindow?.viewWithTag(tag) ?: run {
        statusBarView.tag = tag
        statusBarView.layer.zPosition = 999999.0
        keyWindow?.addSubview(statusBarView)
        statusBarView
    }
}

@Composable
actual fun SetStatusBarColor(color: Color) {
    val statusBar = statusBarView()
    SideEffect {
        statusBar.backgroundColor = color.toUIColor()
        UITabBar.appearance().barTintColor = color.toUIColor()
        UITabBar.appearance().tintColor = color.toUIColor()
        UITabBar.appearance().backgroundColor = color.toUIColor()
    }
}

private fun Color.toUIColor(): UIColor = UIColor(
    red = this.red.toDouble(),
    green = this.green.toDouble(),
    blue = this.blue.toDouble(),
    alpha = this.alpha.toDouble()
)
