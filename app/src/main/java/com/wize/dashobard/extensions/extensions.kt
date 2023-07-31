package com.wize.dashobard.extensions

import android.content.Context
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat

fun Context.color(@ColorRes resId: Int) = ContextCompat.getColor(this, resId)