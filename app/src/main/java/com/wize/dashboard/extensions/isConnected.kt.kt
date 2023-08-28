package com.wize.dashboard.extensions

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.webkit.WebViewClient


fun isOnline(context: Context): Boolean {
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    // For 29 api or above
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork) ?: return false
        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ->    true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) ->   true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ->   true
            else ->     false
        }
    }
    // For below 29 api
    else {
        @Suppress("DEPRECATION")
        if (connectivityManager.activeNetworkInfo != null && connectivityManager.activeNetworkInfo!!.isConnectedOrConnecting) {
            return true
        }
    }
    return false
}

fun isConnectionError(errorCode: Int?): Boolean {
    var isConnectionError = false
    when (errorCode) {
        null -> {
            isConnectionError = false
        }

        WebViewClient.ERROR_TIMEOUT -> {
            isConnectionError = true
        }

        WebViewClient.ERROR_TOO_MANY_REQUESTS -> {
            isConnectionError = true
        }

        WebViewClient.ERROR_CONNECT -> {
            isConnectionError = true
        }

        WebViewClient.ERROR_FAILED_SSL_HANDSHAKE -> {
            isConnectionError = true
        }

        WebViewClient.ERROR_HOST_LOOKUP -> {
            isConnectionError = true
        }

        WebViewClient.ERROR_IO -> {
            isConnectionError = true
        }
    }
    return isConnectionError
}