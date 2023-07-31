package com.wize.dashobard.webview

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.view.ViewGroup
import android.webkit.CookieManager
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.FabPosition
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.wize.dashobard.BuildConfig
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

// or just
import androidx.compose.runtime.*
import com.wize.dashobard.extensions.isOnline

//https://github.com/google/accompanist/issues/1442
@SuppressLint("SetJavaScriptEnabled")
@Composable
fun DashboardWebView(title: String, viewModel: DashboardViewModel, backCallback: (() -> Unit)) {

    var webView by remember { mutableStateOf<WebView?>(null) }

    var isError by remember { mutableStateOf(false) }

    Scaffold(
        floatingActionButton = {
            RefreshButton(isError) {
                webView?.reload()
            }
        },
        floatingActionButtonPosition = FabPosition.End,
    ) { contentPadding ->
        Box(Modifier.padding(contentPadding)) {
            AndroidView(
                factory = {
                    WebView(it).apply {
                        webView = this
                        layoutParams = ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT
                        )
                        webViewClient = object : WebViewClient() {

                            override fun onPageStarted(
                                view: WebView?,
                                url: String,
                                favicon: Bitmap?
                            ) {
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

                            override fun onReceivedError(
                                view: WebView?,
                                request: WebResourceRequest?,
                                error: WebResourceError?
                            ) {
                                super.onReceivedError(view, request, error)
                                webView?.context?.let { context ->
                                    isError = !isOnline(context)
                                }
                            }

                            override fun onPageFinished(view: WebView?, url: String?) {
                                super.onPageFinished(view, url)
                                webView?.context?.let { context ->
                                    isError = !isOnline(context)
                                }
                            }
                        }
                        with(settings) {
                            javaScriptEnabled = true
                            loadsImagesAutomatically = true
                            loadWithOverviewMode = true
                            useWideViewPort = true
                        }
                    }
                },
                update = {
                    it.loadUrl(BuildConfig.DASHBOARD_URL)
                },
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
    }

    BackHandler {
        if (webView?.canGoBack() == true) {
            webView?.goBack()
        } else {
            backCallback.invoke()
        }
    }
}