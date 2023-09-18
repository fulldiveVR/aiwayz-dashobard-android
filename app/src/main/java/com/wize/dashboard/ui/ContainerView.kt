package com.wize.dashboard.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.wize.dashboard.ui.onboarding.OnboardingScreen
import com.wize.dashboard.viewmodel.OnboardingViewModel
import com.wize.dashboard.viewmodel.DashboardViewModel
import com.wize.dashboard.webview.DashboardWebView

@Composable
fun ContainerView(viewModel: OnboardingViewModel, backCallback: (() -> Unit)) {

    val onboardingShownUiState by viewModel.onboardingShownFlow.collectAsStateWithLifecycle()

    if (onboardingShownUiState) {
        DashboardWebView(
            DashboardViewModel()
        ) {
            backCallback.invoke()
        }
    } else {
        OnboardingScreen(viewModel)
    }
}