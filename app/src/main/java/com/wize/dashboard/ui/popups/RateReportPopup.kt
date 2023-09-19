package com.wize.dashboard.ui.popups

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.wize.dashboard.R
import com.wize.dashboard.ui.theme.WizeColor
import com.wize.dashboard.ui.theme.WizeTypography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RateReportPopup(modifier: Modifier, onReportClicked: (report: String) -> Unit) {

    var reportTextState by remember { mutableStateOf("") }

    val cancelButtonColor = ButtonDefaults.buttonColors(
        containerColor = Color.Transparent,
        contentColor = WizeColor.Tertiary
    )

    val okButtonColor = ButtonDefaults.buttonColors(
        containerColor = Color.Transparent,
        contentColor = WizeColor.Accent
    )

    val openDialog = remember { mutableStateOf(true) }
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
                        modifier = modifier.padding(top = 16.dp, bottom = 16.dp),
                        text = stringResource(R.string.rate_popup_title),
                        style = WizeTypography.titleLarge
                    )

                    TextField(
                        value = reportTextState,
                        onValueChange = { reportTextState = it },
                        shape = RoundedCornerShape(8.dp),
                        colors = TextFieldDefaults.textFieldColors(
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = WizeColor.Tertiary,
                            containerColor = WizeColor.Background
                        )
                    )

                    Row(modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        TextButton(
                            modifier = modifier.padding(top = 20.dp),
                            onClick = { openDialog.value = false },
                            colors = cancelButtonColor
                        ) {
                            Text(
                                stringResource(R.string.button_not_now),
                                style = WizeTypography.titleMedium
                            )
                        }
                        TextButton(
                            modifier = modifier.padding(top = 20.dp),
                            onClick = {
                                onReportClicked.invoke(reportTextState)
                                openDialog.value = false
                            },
                            colors = okButtonColor
                        ) {
                            Text(
                                stringResource(R.string.button_report),
                                style = WizeTypography.titleMedium
                            )
                        }
                    }
                }
            }
        }
    }
}