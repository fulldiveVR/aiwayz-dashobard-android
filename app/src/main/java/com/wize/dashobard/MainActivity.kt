package com.wize.dashobard

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.wize.dashobard.ui.theme.AiwayzdashobardandroidTheme
import com.wize.dashobard.webview.DashboardViewModel
import com.wize.dashobard.webview.DashboardWebView

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AiwayzdashobardandroidTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    DashboardPreview()
                }
            }
        }
    }
}

@Preview(showBackground = false)
@Composable
fun DashboardPreview() {
    AiwayzdashobardandroidTheme {
        DashboardWebView(stringResource(R.string.app_name), DashboardViewModel())
    }
}