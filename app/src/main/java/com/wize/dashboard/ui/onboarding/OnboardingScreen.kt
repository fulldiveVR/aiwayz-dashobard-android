package com.wize.dashboard.ui.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.lifecycle.coroutineScope
import com.wize.dashboard.viewmodel.OnboardingViewModel
import kotlinx.coroutines.launch

@Composable
fun OnboardingScreen(viewModel: OnboardingViewModel) {

    val lifecycle = LocalLifecycleOwner.current.lifecycle

    val onboardPages = onboardPagesList

    val currentPage = remember { mutableIntStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(onboardPages[currentPage.intValue].background)
    ) {
        OnboardingPageView(
            modifier = Modifier
                .weight(1f)
                .padding(24.dp),
            currentPage = onboardPages[currentPage.intValue]
        )

        OnBoardingNavButton(
            modifier = Modifier.padding(start = 24.dp, end = 24.dp, bottom = 16.dp),
            currentPage = currentPage.intValue,
            noOfPages = onboardPages.size,
            onNextClicked = { currentPage.intValue++ },
            onFinishClicked = {
                lifecycle.coroutineScope.launch {
                    viewModel.setOnboardingShown()
                }
            }
        )
        OnboardingTabSelector(
            modifier = Modifier
                .background(onboardPages[currentPage.intValue].background)
                .align(Alignment.CenterHorizontally),
            onboardPages = onboardPages,
            currentPage = currentPage.intValue
        ) { index ->
            currentPage.intValue = index
        }
    }
}