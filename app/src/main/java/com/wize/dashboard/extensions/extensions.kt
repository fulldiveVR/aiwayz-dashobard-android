package com.wize.dashboard.extensions

import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.webkit.MimeTypeMap
import androidx.annotation.ColorRes
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.core.content.ContextCompat
import com.wize.dashboard.R
import java.util.UUID

val NotoSansFont = FontFamily(
    Font(R.font.noto_sans_regular, FontWeight.Normal),
    Font(R.font.noto_sans_semibold, FontWeight.SemiBold),
)

fun Context.color(@ColorRes resId: Int) = ContextCompat.getColor(this, resId)

inline fun <reified T> unsafeLazy(noinline initializer: () -> T): Lazy<T> =
    lazy(LazyThreadSafetyMode.NONE, initializer)

fun Context.openAppInGooglePlay(appPackageName: String? = null) {
    val packName = appPackageName ?: packageName
    try {
        startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse("market://details?id=$packName")
            ).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        )
    } catch (anfe: android.content.ActivityNotFoundException) {
        startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://play.google.com/store/apps/details?id=$packName")
            ).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        )
    }
}

fun isBlobUrl(url: String): Boolean {
    return url.startsWith("blob:")
}

fun isDataUrl(url: String?): Boolean {
    return null != url && url.startsWith("data:")
}

fun isPermissionsGranted(context: Context, permissions: Array<out String>): Boolean {
    return permissions.all { permission ->
        isPermissionGranted(context, permission)
    }
}

fun isPermissionGranted(context: Context, permission: String): Boolean {
    return (ContextCompat.checkSelfPermission(
        context,
        permission
    ) == PackageManager.PERMISSION_GRANTED)
}

fun getDownloadManager(context: Context): DownloadManager {
    return context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
}

inline fun <T, R> T?.letOr(block: (T) -> R, blockElse: () -> R): R {
    return if (this != null) block.invoke(this) else blockElse.invoke()
}

fun fetchFileName(filename: String, mimeType: String): String {
    return when {
        filename.isNotEmpty() -> filename
        else -> {
            val ext = if (mimeType == "image/jpeg" || mimeType == "image/jpg") "jpg"
            else MimeTypeMap.getSingleton().getExtensionFromMimeType(mimeType)
            return UUID.randomUUID().toString() + ext?.letOr({ ".$it" }, { "" })
        }
    }
}