package com.wize.dashobard.webview

import androidx.compose.foundation.layout.padding
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.wize.dashobard.R

@Composable
fun RefreshButton(isVisible: Boolean, refreshCallback: (() -> Unit)) {
    if (isVisible) {
        FloatingActionButton(
            onClick = { refreshCallback.invoke() },
            backgroundColor = colorResource(R.color.colorAccent),
            contentColor = Color.White,
            modifier = Modifier
                .padding(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 56.dp)
        ) {
            Icon(Icons.Filled.Refresh, "")
        }
    }
}