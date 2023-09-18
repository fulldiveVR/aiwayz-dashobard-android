package com.wize.dashboard.extensions

import android.content.Context
import androidx.annotation.ColorRes
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.core.content.ContextCompat
import com.wize.dashboard.R

val NotoSansFont = FontFamily(
    Font(R.font.noto_sans_regular, FontWeight.Normal),
    Font(R.font.noto_sans_semibold, FontWeight.SemiBold),
)

fun Context.color(@ColorRes resId: Int) = ContextCompat.getColor(this, resId)

inline fun <reified T> unsafeLazy(noinline initializer: () -> T): Lazy<T> =
    lazy(LazyThreadSafetyMode.NONE, initializer)