package com.wize.dashboard.extensions

import android.util.Log
import android.webkit.JavascriptInterface
import okhttp3.OkHttpClient
import org.json.JSONObject
import java.util.concurrent.TimeUnit


@Suppress("Unused")
class ChromeExtension {

    var onCopyToClipboard: ((String?) -> Unit)? = null
    var onBlobReceiver: ((String) -> Unit)? = null


    private val storage = mutableMapOf<String, Any>()
    private val okHttpClient by unsafeLazy {
        OkHttpClient.Builder().apply {
            readTimeout(10, TimeUnit.SECONDS)
            connectTimeout(20, TimeUnit.SECONDS)
            writeTimeout(10, TimeUnit.SECONDS)
        }
            .build()
    }

    @JavascriptInterface
    fun writeToClipboard(text: String?) {
        onCopyToClipboard?.invoke(text)
    }

    @android.webkit.JavascriptInterface
    fun set(obj: String) {
        Log.d(TAG, "chrome.storage.set >>>> $obj")
        val jsonObject = JSONObject(obj)
        jsonObject.keys().forEach { key ->
            storage[key] = jsonObject.get(key)
        }
    }

    @android.webkit.JavascriptInterface
    fun remove(obj: String) {
        Log.d(TAG, "chrome.storage.remove >>>> $obj")
        val jsonObject = JSONObject(obj)
        jsonObject.keys().forEach { key ->
            storage.remove(jsonObject.get(key))
        }
    }

    @android.webkit.JavascriptInterface
    fun get(): String {
        Log.d(TAG, "chrome.storage.get >>>> $storage")
        return JSONObject(storage.toMutableMap<Any?, Any?>()).toString()
    }


    @android.webkit.JavascriptInterface
    fun ogResultFailed(resourceId: String) {
        Log.e(TAG, "ogResultFailed: $resourceId")
    }

    @JavascriptInterface
    fun getBlobData64(data: String) {
        onBlobReceiver?.invoke(data)
    }

    companion object {
        private const val TAG = "TestB"
        const val JS_INJECTED_OBJECT = "chrome_extension"
        const val JS_COPY_TEXT_TO_CLIPBOARD =
            "javascript:navigator.clipboard.writeText = (data) => {return $JS_INJECTED_OBJECT.writeToClipboard(data);}"
        const val JS_BLOB_FETCHER =
            "function _____fd_blobRequest(){" +
                "    var xhr = new XMLHttpRequest();" +
                "    xhr.open('GET', '%1\$s', true);" +
                "    xhr.setRequestHeader('Content-type','%2\$s');" +
                "    xhr.responseType = 'blob';  " +

                "    xhr.onloadend = function(e) {" +

                "        if (this.status == 200) {" +
                "            var reader = new FileReader();" +
                "            reader.readAsDataURL(this.response);" +
                "            reader.onloadend = function() {" +
                "                chrome_extension.getBlobData64(reader.result);" +
                "            }" +
                "        }" +
                "    };" +
                "    xhr.send();" +
                "};" +
                "_____fd_blobRequest();";
    }
}