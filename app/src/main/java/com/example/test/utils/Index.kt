package com.example.test.utils

import android.content.Context
import android.util.TypedValue
// 将dp转成px
fun dpToPx(dp: Float, context: Context): Float {
    val metrics = context.resources.displayMetrics
    return dp * (metrics.densityDpi / 160f)
}

// 将sp转成px
fun Context.spToPx(spValue: Float): Float {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_SP,
        spValue,
        resources.displayMetrics
    )
}