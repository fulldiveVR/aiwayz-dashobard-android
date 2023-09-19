package com.wize.dashboard.ui.popups

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.runtime.*
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.wize.dashboard.ui.theme.WizeColor
import com.wize.dashboard.ui.theme.WizeTypography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RateUsPopup(
    modifier: Modifier,
    onRateClicked: (rate: Int) -> Unit
) {

    val cancelButtonColor = ButtonDefaults.buttonColors(
        containerColor = Color.Transparent,
        contentColor = WizeColor.Tertiary
    )

    val okButtonColor = ButtonDefaults.buttonColors(
        containerColor = Color.Transparent,
        contentColor = WizeColor.Accent
    )

    val openDialog = remember { mutableStateOf(true) }

    var currentRating by remember { mutableIntStateOf(4) }

    if (openDialog.value) {
        androidx.compose.material3.AlertDialog(
            onDismissRequest = {
                openDialog.value = false
            },
        ) {
            Surface(
                modifier = modifier
                    .wrapContentWidth()
                    .wrapContentHeight(),
                shape = MaterialTheme.shapes.large
            ) {
                Column(modifier = modifier.padding(16.dp)) {
                    Text(
                        text = "RATE US",
                        modifier.padding(top = 20.dp, bottom = 20.dp),
                        style = WizeTypography.titleLarge
                    )
                    Text(
                        text = "Do you like Fulldive? We would love to hear your feedback and make the app better for you.",
                        modifier = modifier.padding(bottom = 20.dp),
                        style = WizeTypography.bodyMedium
                    )

                    Row(modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                        RatingBar(
                            currentRating = currentRating,
                            onRatingChanged = { currentRating = it })
                    }

                    Row(modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        TextButton(
                            modifier = modifier.padding(top = 20.dp),
                            onClick = { openDialog.value = false },
                            colors = cancelButtonColor
                        ) {
                            Text("NOT NOW", style = WizeTypography.titleMedium)
                        }
                        TextButton(
                            modifier = modifier.padding(top = 20.dp),
                            onClick = { onRateClicked.invoke(currentRating) },
                            colors = okButtonColor
                        ) {
                            Text("RATE", style = WizeTypography.titleMedium)
                        }
                    }
                }
            }
        }
    }
}