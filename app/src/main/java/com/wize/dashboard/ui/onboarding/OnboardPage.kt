package com.wize.dashboard.ui.onboarding

import androidx.compose.ui.graphics.Color
import com.wize.dashboard.R
import com.wize.dashboard.ui.theme.WizeColor

data class OnboardPage(
    val imageRes: Int,
    val titleRes: Int,
    val descriptionRes: Int,
    val background: Color
)

val onboardPagesList = listOf(
    OnboardPage(
        imageRes = R.drawable.onboarding_image_1,
        titleRes = R.string.onboarding_title_1,
        descriptionRes = R.string.onboarding_description_1,
        background = WizeColor.OnboardingBackground1
    ), OnboardPage(
        imageRes = R.drawable.onboarding_image_2,
        titleRes = R.string.onboarding_title_2,
        descriptionRes = R.string.onboarding_description_2,
        background = WizeColor.OnboardingBackground2
    ), OnboardPage(
        imageRes = R.drawable.onboarding_image_3,
        titleRes = R.string.onboarding_title_3,
        descriptionRes = R.string.onboarding_description_3,
        background = WizeColor.OnboardingBackground3
    )
)