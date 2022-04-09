package com.randomimagecreator.helpers

import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.annotation.RequiresApi
import java.io.File
import java.io.FileOutputStream

/**
 * Helper class for saving images.
 */
class ImageSaver {
    companion object {
        /**
         * Saves the given array of bitmaps to the user his external storage.
         *
         * @param bitmaps           A list of all bitmaps to save.
         * @param context           The context of the caller.
         * @param directory         Name of the directory for saving.
         */
        fun saveBitmaps(
            bitmaps: MutableList<Bitmap>,
            context: Context,
            directory: String
        ): ArrayList<Uri> {
            val savedBitmapUris = arrayListOf<Uri>()
            for (bitmap in bitmaps) {
                val uri = if (Build.VERSION.SDK_INT >= 29) {
                    saveBitmapUsingMediaStore(bitmap, context, directory)
                } else {
                    saveBitmapExternalFilesDir(bitmap, context, directory)
                }
                savedBitmapUris.add(uri)
            }
            return savedBitmapUris
        }

        @RequiresApi(Build.VERSION_CODES.Q)
        private fun saveBitmapUsingMediaStore(
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

        private fun saveBitmapExternalFilesDir(
            bitmap: Bitmap,
            context: Context,
            directory: String
        ): Uri {
            val fileName = createUniqueFileName().toString() + "${directory}/.jpg"
            val file = File(context.getExternalFilesDir(null), fileName)
            FileOutputStream(file).use { stream ->
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
            }

            return Uri.fromFile(file)
        }

        /**
         * Creates a 'unique' name based on the current epoch time in milliseconds.
         */
        private fun createUniqueFileName() = System.currentTimeMillis()
    }
}
