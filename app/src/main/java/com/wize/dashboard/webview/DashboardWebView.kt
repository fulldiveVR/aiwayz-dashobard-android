package com.wize.dashboard.webview

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.util.Log
import android.view.ViewGroup
import android.webkit.CookieManager
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.wize.dashboard.BuildConfig
import com.wize.dashboard.extensions.ChromeExtension
import com.wize.dashboard.extensions.ChromeExtension.Companion.JS_COPY_TEXT_TO_CLIPBOARD
import com.wize.dashboard.extensions.ChromeExtension.Companion.JS_INJECTED_OBJECT
import com.wize.dashboard.extensions.isBlobUrl
import com.wize.dashboard.extensions.isDataUrl
import com.wize.dashboard.ui.ShimmerLayout
import com.wize.dashboard.ui.SwipeState
import com.wize.dashboard.ui.WizeRefreshLayout
import com.wize.dashboard.viewmodel.DashboardViewModel

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun DashboardWebView(viewModel: DashboardViewModel, backCallback: (() -> Unit)) {

    val chromeExtension by remember { mutableStateOf(ChromeExtension()) }
    var webViewLayout by remember { mutableStateOf<WebView?>(null) }

    var isLoading by remember { mutableStateOf(true) }

    AndroidView(
        factory = { context ->
            val swipe = WizeRefreshLayout(context).apply {
                setOnRefreshListener {
                    if (state == SwipeState.SWIPE) {
                        webViewLayout?.reload()
                    }
                    isRefreshing = false
                }
            }

            val webview = WebView(context).also { webView ->

                webView.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT,
                )
                webView.webViewClient = object : WebViewClient() {

                    override fun onPageStarted(
                        view: WebView?,
                        url: String,
                        favicon: Bitmap?
                    ) {
                        super.onPageStarted(view, url, favicon)

                        if (url == BuildConfig.REDIRECT_SCHEME) {
                            webView.loadUrl(BuildConfig.DASHBOARD_URL)
                        } else {
                            super.onPageStarted(view, url, favicon)
                        }

                        webView.evaluateJavascript(JS_COPY_TEXT_TO_CLIPBOARD, null)
                    }

                    override fun onReceivedHttpError(
                        view: WebView?,
                        request: WebResourceRequest?,
                        errorResponse: WebResourceResponse?
                    ) {

                        val cookies = CookieManager.getInstance()
                            ?.getCookie(BuildConfig.DASHBOARD_URL)

                        val isAuthorized = cookies != null && cookies.contains("rf=")
                        if (!isAuthorized && webView.url != BuildConfig.REDIRECT_URL_AUTH) {
                            webView.loadUrl(BuildConfig.REDIRECT_URL_AUTH)
                        } else {
                            super.onReceivedHttpError(view, request, errorResponse)
                        }
                    }

                    override fun onPageFinished(view: WebView?, url: String?) {
                        super.onPageFinished(view, url)
                        isLoading = false
                    }
                }

                with(webView.settings) {
                    javaScriptEnabled = true
                    loadsImagesAutomatically = true
                    loadWithOverviewMode = true
                    useWideViewPort = true
                    domStorageEnabled = true
                }

                chromeExtension.onCopyToClipboard = { text ->
                    viewModel.copyToClipboard(text)
                }

                webView.addJavascriptInterface(chromeExtension, JS_INJECTED_OBJECT)

                webView.setDownloadListener { url, _, _, mimetype, _ ->
                    if (isBlobUrl(url)) {
                        viewModel.fetchBlob(chromeExtension, webView, url, mimetype)
                    } else if (isDataUrl(url)) {
                        viewModel.saveData(url, mimetype)
                    }
                }

                webViewLayout = webView
            }

            swipe.addView(webview)
            swipe
        },
        update = {
            webViewLayout?.loadUrl(BuildConfig.DASHBOARD_URL)
        },
        modifier = Modifier
            .fillMaxSize()
    )

    ShimmerLayout(isLoading)

    BackHandler {
        if (webViewLayout?.canGoBack() == true) {
            webViewLayout?.goBack()
        } else {
            backCallback.invoke()
        }
    }
}