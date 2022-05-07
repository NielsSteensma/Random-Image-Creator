package com.randomimagecreator.helpers

import android.content.ContentResolver
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import androidx.documentfile.provider.DocumentFile

/**
 * Helper class for saving images.
 */
class ImageSaver {
    companion object {
        /**
         * Saves the given array of bitmaps to the provided [directory].
         *
         * @param bitmaps           A list of all bitmaps to save.
         * @param context           The context of the caller.
         * @param directory         Directory selected by user for saving.
         */
        fun saveBitmaps(
            bitmaps: MutableList<Bitmap>,
            context: Context,
            directory: Uri
        ): List<Uri> {
            val rootDocumentFile = DocumentFile.fromTreeUri(context, directory)
            assert(rootDocumentFile != null) { "root document file shouldn't be null" }

            val bitmapUris = mutableListOf<Uri>()

            for (bitmap in bitmaps) {
                saveBitmap(bitmap, context.contentResolver, rootDocumentFile!!)?.let {
                    bitmapUris.add(it)
                }
            }
            return bitmapUris
        }

        private fun saveBitmap(
            bitmap: Bitmap,
            contentResolver: ContentResolver,
            rootDocumentFile: DocumentFile
        ): Uri? {
            val fileName = createUniqueFileName().toString()
            val createdFile = rootDocumentFile.createFile("image/jpeg", fileName) ?: return null
            contentResolver.openOutputStream(createdFile.uri).use { stream ->
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
            }
            return createdFile.uri
        }

        /**
         * Creates a 'unique' name based on the current epoch time in milliseconds.
         */
        private fun createUniqueFileName() = System.currentTimeMillis()
    }
}