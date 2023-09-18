package com.wize.dashboard.ui.onboarding

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.wize.dashboard.R
import com.wize.dashboard.ui.theme.WizeColor
import com.wize.dashboard.ui.theme.WizeTypography

@Composable
fun OnBoardingNavButton(
    modifier: Modifier,
    currentPage: Int,
    noOfPages: Int,
    onNextClicked: () -> Unit,
    onFinishClicked: () -> Unit
) {

    val mainButtonColor = ButtonDefaults.buttonColors(
        containerColor = WizeColor.Accent,
        contentColor = WizeColor.Background
    )

    Button(
        shape = RoundedCornerShape(8.dp),
        colors = mainButtonColor,
        onClick = {
            if (currentPage < noOfPages - 1) {
                onNextClicked()
            } else {
                onFinishClicked()
            }
        },
        modifier = modifier
            .fillMaxWidth()
            .height(44.dp),
    )
    {
        Text(
            text = if (currentPage < noOfPages - 1) {
                stringResource(R.string.button_next)
            } else {
                stringResource(R.string.button_lets_go)
            },
            style = WizeTypography.bodyMedium
        )
    }
}