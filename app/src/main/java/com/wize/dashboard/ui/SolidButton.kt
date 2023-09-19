package com.wize.dashboard.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.wize.dashboard.ui.theme.WizeColor
import com.wize.dashboard.ui.theme.WizeTypography

@Composable
fun SolidButton(modifier: Modifier, text: String, onClick: () -> Unit) {

    val buttonColor = ButtonDefaults.buttonColors(
        containerColor = WizeColor.Accent,
        contentColor = WizeColor.Background
    )

    Button(
        shape = RoundedCornerShape(8.dp),
        colors = buttonColor,
        modifier = modifier
            .fillMaxWidth()
            .height(44.dp),
        onClick = {
            onClick.invoke()
        },
        content = {
            Text(
                text = text,
                style = WizeTypography.bodyMedium
            )
        }
    )
}