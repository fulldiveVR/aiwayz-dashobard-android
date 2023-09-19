package com.wize.dashboard.ui.onboarding

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.wize.dashboard.R
import com.wize.dashboard.ui.SolidButton

@Composable
fun OnBoardingNavButton(
    modifier: Modifier,
    currentPage: Int,
    noOfPages: Int,
    onNextClicked: () -> Unit,
    onFinishClicked: () -> Unit
) {
    SolidButton(
        modifier = modifier,
        onClick = {
            if (currentPage < noOfPages - 1) {
                onNextClicked()
            } else {
                onFinishClicked()
            }
        },
        text = if (currentPage < noOfPages - 1) {
            stringResource(R.string.button_next)
        } else {
            stringResource(R.string.button_lets_go)
        }
    )
}