package com.wize.dashboard.viewmodel

import android.Manifest
import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.webkit.MimeTypeMap
import android.webkit.ValueCallback
import android.webkit.WebView
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModel
import com.wize.dashboard.extensions.ChromeExtension
import com.wize.dashboard.extensions.fetchFileName
import com.wize.dashboard.extensions.isPermissionsGranted
import com.wize.dashboard.extensions.letOr
import java.io.File
import java.io.FileOutputStream
import java.util.Date
import java.util.UUID

class DashboardViewModel(val activity: Activity) : ViewModel() {

    fun fetchBlob(
        chromeExtension: ChromeExtension,
        webView: WebView,
        blobUrl: String,
        contentType: String
    ) {
        val script = ChromeExtension.JS_BLOB_FETCHER.format(
            blobUrl,
            contentType
        )
        chromeExtension.onBlobReceiver = { data ->
            saveData(data, contentType)
        }
        executeJS(webView, script)
    }

    private fun executeJS(
        webView: WebView,
        jscode: String,
        resultCallback: ValueCallback<String>? = null
    ) {
        try {
            if (jscode.isNotEmpty()) {
                Log.d("TAG", "executeJS: ${webView.url}>> $jscode")
                webView.evaluateJavascript(jscode, resultCallback)
            }
        } catch (ex: Exception) {
            Log.e("TAG", "executeJS: ", ex)
        }
    }

    private fun saveData(base64Data: String, mimeType: String) {
        val permissions = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        if (isPermissionsGranted(activity, permissions)) {
            val delimiterIndex = base64Data.indexOf(",")
            val pair = if (delimiterIndex > 0) {
                val header = base64Data.substring(5, delimiterIndex)
                val body = base64Data.substring(delimiterIndex + 1)
                val delimiter2Index = header.indexOf(";")
                val dataMimeType = if (delimiter2Index > 0) {
                    header.substring(0, delimiter2Index)
                } else {
                    header
                }
                Pair(
                    if (header.endsWith("base64")) {
                        Base64.decode(body, 0)
                    } else {
                        body.toByteArray()
                    },
                    dataMimeType
                )
            } else {
                Pair(base64Data.toByteArray(), mimeType)
            }
            writeToDownloads(
                pair.first,
                pair.second,
            )
        } else {
            ActivityCompat.requestPermissions(
                activity,
                permissions,
                101
            )
        }
    }

    private fun writeToDownloads(
        fileData: ByteArray,
        mimeType: String,
    ) {
        val filename = fetchFileName("", mimeType)
        Log.d("TestB", "filename $filename")
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            val file = File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                filename
            )
            FileOutputStream(file).use { out ->
                out.write(fileData)
                out.flush()
            }
        } else {
            Log.d("TestB", "writeToDownloads: $fileData")
            val contentResolver: ContentResolver = activity.contentResolver
            val contentValues = ContentValues().apply {
                put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
                put(MediaStore.MediaColumns.MIME_TYPE, mimeType)
                put(
                    MediaStore.MediaColumns.RELATIVE_PATH,
                    Environment.DIRECTORY_DOWNLOADS + File.separator
                )
            }
            val uri = contentResolver
                .insert(
                    MediaStore.Downloads.getContentUri(MediaStore.VOLUME_EXTERNAL),
                    contentValues
                )
            uri?.let(contentResolver::openOutputStream)
                ?.use { os ->
                    os.write(fileData)
                    os.flush()
                }
        }
    }

    fun copyToClipboard(text: String?) {
        val clipboard = activity
            .getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("copy", text)
        clipboard.setPrimaryClip(clip)
    }
}