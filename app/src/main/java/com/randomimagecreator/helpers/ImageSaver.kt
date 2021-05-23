package com.randomimagecreator.helpers

import android.content.ContentResolver
import android.content.ContentValues
import android.graphics.Bitmap
import android.provider.MediaStore

/**
 * Helper class for saving images
 */
class ImageSaver {
    companion object {
        /**
         * Saves the given bitmap to the user his external storage
         */
        fun saveBitmap(bitmap: Bitmap, contentResolver: ContentResolver) {
            val downloads = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            val values = ContentValues().apply {
                put(MediaStore.Images.Media.DISPLAY_NAME, "TestRandonImageCreator")
                //put(MediaStore.Images.Media.IS_PENDING, true)
            }
            val uri = contentResolver.insert(downloads, values)
            contentResolver.openOutputStream(uri!!)
                .use { stream -> bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream) }
        }
    }
}