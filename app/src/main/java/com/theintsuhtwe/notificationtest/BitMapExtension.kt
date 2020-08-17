package com.theintsuhtwe.notificationtest

import android.app.Application
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap

fun drawableToBitmapExtension(drawable : Drawable) : Bitmap{
    val bitmap : Bitmap= drawable?.toBitmap()
    return bitmap
}