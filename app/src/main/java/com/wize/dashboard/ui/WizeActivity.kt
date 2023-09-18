package com.wize.dashboard.ui

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView
import androidx.core.view.ViewCompat
import androidx.lifecycle.ViewModelProvider
import com.wize.dashboard.R
import com.wize.dashboard.model.SettingsManager
import com.wize.dashboard.ui.popups.RateUsPopup
import com.wize.dashboard.viewmodel.OnboardingViewModel
import com.wize.dashboard.ui.theme.WizeTheme

open class WizeActivity : ComponentActivity() {

    private val viewModel by lazy {
        ViewModelProvider(
            this,
            OnboardingViewModel.Factory(SettingsManager(application))
        )[OnboardingViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WizeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    ContainerView(viewModel) { finish() }
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
}