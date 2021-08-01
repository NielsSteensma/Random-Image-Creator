package com.randomimagecreator.helpers

import android.content.ContentResolver
import android.content.ContentValues
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import java.util.*

/**
 * Helper class for saving images.
 */
class ImageSaver {
    companion object {
        /**
         * Saves the given array of bitmaps to the user his external storage.
         *
         * @param bitmaps           A list of all bitmaps to save.
         * @param contentResolver  The contentResolver to use for saving.
         * @param directory         Name of the directory to create inside
         *                            "Pictures/RandomImageCreator" for saving.
         */
        fun saveBitmaps(
            bitmaps: MutableList<Bitmap>,
            contentResolver: ContentResolver,
            directory: String
        ): ArrayList<Uri> {
            val savedBitmapUris = arrayListOf<Uri>()
            for (bitmap in bitmaps) {
                savedBitmapUris.add(saveBitmap(bitmap, contentResolver, directory))
            }
            return savedBitmapUris;
        }

        /**
         * Saves the given bitmap to the user his external storage.
         */
        private fun saveBitmap(
            bitmap: Bitmap,
            contentResolver: ContentResolver,
            directory: String
        ): Uri {
            val values = ContentValues().apply {
                put(MediaStore.Images.Media.DISPLAY_NAME, createUniqueFileName())
                put(
                    MediaStore.Images.Media.RELATIVE_PATH,
                    "Pictures/RandomImageCreator/${directory}"
                )
                put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
            }
            val contentUri =
                MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
            val uri = contentResolver.insert(contentUri, values)
            contentResolver.openOutputStream(uri!!)
                .use { stream -> bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream) }
            return uri
        }

        /**
         * Creates an 'unique' name based on the current epoch time in milliseconds.
         */
        private fun createUniqueFileName() =
            System.currentTimeMillis()
    }
}