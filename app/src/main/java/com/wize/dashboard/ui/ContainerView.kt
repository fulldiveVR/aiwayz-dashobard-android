package com.wize.dashboard.ui

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.wize.dashboard.model.PopupsViewModel
import com.wize.dashboard.model.StartAppPopup
import com.wize.dashboard.ui.onboarding.OnboardingScreen
import com.wize.dashboard.ui.popups.RateReportPopup
import com.wize.dashboard.ui.popups.RateUsPopup
import com.wize.dashboard.viewmodel.OnboardingViewModel
import com.wize.dashboard.viewmodel.DashboardViewModel
import com.wize.dashboard.webview.DashboardWebView

@Composable
fun ContainerView(
    onboardingViewModel: OnboardingViewModel,
    popupsViewModel: PopupsViewModel,
    backCallback: (() -> Unit)
) {

    val activity = LocalContext.current as Activity

    val onboardingShownUiState by onboardingViewModel.onboardingShownFlow.collectAsStateWithLifecycle()

    val currentPopupUiState by popupsViewModel.currentPopupFlow.collectAsStateWithLifecycle()
    val rateUsDoneUiState by popupsViewModel.rateUsDoneFlow.collectAsStateWithLifecycle()
    val rateUsReportShowUiState by popupsViewModel.rateUsReportFlow.collectAsStateWithLifecycle()

    if (onboardingShownUiState) {
        DashboardWebView(
            DashboardViewModel(activity)
        ) {
            backCallback.invoke()
        }

        when {
            currentPopupUiState == StartAppPopup.RateUs && !rateUsDoneUiState && !rateUsReportShowUiState -> {
                RateUsPopup(
                    modifier = Modifier,
                    onRateClicked = { rate ->
                        popupsViewModel.onRateUsPositiveClicked(
                            activity,
                            rate
                        )
                    })
            }

            rateUsReportShowUiState -> {
                RateReportPopup(modifier = Modifier) { report ->
                    popupsViewModel.sendMessage(report)
                }
            }

            else -> Unit
        }

    } else {
        OnboardingScreen(onboardingViewModel)
    }
}