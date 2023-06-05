package com.wize.dashobard.webview

import android.annotation.SuppressLint
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.TopAppBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.constraintlayout.compose.ConstraintLayout
import com.wize.dashobard.R

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun DashboardWebView(title: String, modifier: Modifier = Modifier) {

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 0.dp, top = 0.dp, end = 0.dp, bottom = 2.dp)
    ) {
        val (appBarRes, webViewRef) = createRefs()

        TopAppBar(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 0.dp, top = 0.dp, end = 0.dp, bottom = 0.dp)
                .constrainAs(appBarRes) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                },
            backgroundColor = colorResource(R.color.colorAccent),
            title = {
                Text(
                    text = title,
                    maxLines = 1,
                    color = colorResource(R.color.textColorAccent),
                    overflow = TextOverflow.Ellipsis
                )
            },
            navigationIcon = {
                Icon(
                    painterResource(R.drawable.ic_launcher_foreground),
                    contentDescription = "",
                    tint = colorResource(R.color.colorBackground),
                    modifier = Modifier
                        .padding(start = 4.dp, top = 0.dp, end = 8.dp, bottom = 0.dp)
                        .clickable {}
                )
            },
        )

        val mUrl = "https://tw.aiwize.com"

        AndroidView(
            factory = {
                WebView(it).apply {
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                    webViewClient = WebViewClient()
                    with(settings) {
                        javaScriptEnabled = true
                        loadWithOverviewMode = true
                        useWideViewPort = true
                        builtInZoomControls = true
                    }
                }
            },
            update = {
                it.loadUrl(mUrl)
            },
            modifier = Modifier
                .padding(start = 0.dp, top = 0.dp, end = 0.dp, bottom = 16.dp)
                .fillMaxWidth()
                .constrainAs(webViewRef) {
                    start.linkTo(parent.start)
                    top.linkTo(appBarRes.bottom)
                    end.linkTo(parent.end)
                },
        )
    }
}