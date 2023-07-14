package com.wize.dashobard.webview

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.view.ViewGroup
import android.webkit.CookieManager
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
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
import com.wize.dashobard.BuildConfig
import com.wize.dashobard.R

//https://github.com/google/accompanist/issues/1442
@SuppressLint("SetJavaScriptEnabled")
@Composable
fun DashboardWebView(title: String, viewModel: DashboardViewModel, backCallback: (() -> Unit)) {

    var webView: WebView? = null

    AndroidView(
        factory = {
            WebView(it).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                webViewClient = object : WebViewClient() {

                    override fun onPageStarted(view: WebView?, url: String, favicon: Bitmap?) {
                        super.onPageStarted(view, url, favicon)
                        if (url == BuildConfig.REDIRECT_SCHEME) {
                            loadUrl(BuildConfig.DASHBOARD_URL)
                        } else {
                            super.onPageStarted(view, url, favicon)
                        }
                    }

                    override fun onReceivedHttpError(
                        view: WebView?,
                        request: WebResourceRequest?,
                        errorResponse: WebResourceResponse?
                    ) {

                        val cookies = CookieManager.getInstance()
                            ?.getCookie(BuildConfig.DASHBOARD_URL)

                        val isAuthorized = cookies != null && cookies.contains("rf=")

                        if (!isAuthorized && url != BuildConfig.REDIRECT_URL_AUTH) {
                            loadUrl(BuildConfig.REDIRECT_URL_AUTH)
                        } else {
                            super.onReceivedHttpError(view, request, errorResponse)
                        }
                    }
                }
                with(settings) {
                    javaScriptEnabled = true
                    loadsImagesAutomatically = true
                    loadWithOverviewMode = true
                    useWideViewPort = true
                }
                webView = this
            }
        },
        update = {
             it.loadUrl(BuildConfig.DASHBOARD_URL)
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 56.dp)
    )

    BackHandler {
        if (webView?.canGoBack() == true) {
            webView?.goBack()
        } else {
            backCallback.invoke()
        }
    }

    TopAppBar(
        modifier = Modifier
            .fillMaxWidth(),
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
        actions = {
            Icon(
                painterResource(R.drawable.ic_refresh),
                contentDescription = "",
                tint = colorResource(R.color.colorBackground),
                modifier = Modifier
                    .padding(start = 8.dp, top = 0.dp, end = 16.dp, bottom = 0.dp)
                    .clickable {
                        webView?.reload()
                    }
            )
        }
    )
}