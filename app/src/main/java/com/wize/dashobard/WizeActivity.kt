package com.wize.dashobard

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.platform.LocalView
import androidx.core.view.ViewCompat
import com.wize.dashobard.ui.theme.AiwayzdashobardandroidTheme
import com.wize.dashobard.webview.DashboardViewModel
import com.wize.dashobard.webview.DashboardWebView

class WizeActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AiwayzdashobardandroidTheme {
                DashboardWebView(
                    DashboardViewModel()
                ) { finish() }
            }
            StatusBarColor()
        }
    }
}

@Composable
fun StatusBarColor() {
    val view = LocalView.current
    val darkTheme = isSystemInDarkTheme()

    if (!view.isInEditMode) {
        SideEffect {
            (view.context as Activity).window.statusBarColor =
                view.context.getColor(R.color.colorPrimaryDark)
            ViewCompat.getWindowInsetsController(view)?.isAppearanceLightStatusBars = false
            ViewCompat.getWindowInsetsController(view)?.isAppearanceLightNavigationBars = false
        }
    }
}