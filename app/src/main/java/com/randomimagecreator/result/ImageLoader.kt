package com.randomimagecreator.result

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri


class ImageLoader(private val context: Context) {
    fun loadImageBitmap(uri: Uri): Bitmap {
        val parcelFileDescriptor = context.contentResolver.openFileDescriptor(uri, "r")
        val image = BitmapFactory.decodeFileDescriptor(parcelFileDescriptor?.fileDescriptor)
        parcelFileDescriptor?.close()
        return image
    }
}