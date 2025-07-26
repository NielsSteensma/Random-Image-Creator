package com.randomimagecreator.common

import android.content.ContentResolver
import android.graphics.Bitmap
import android.net.Uri
import androidx.documentfile.provider.DocumentFile
import com.randomimagecreator.configuration.ImageFileFormat

/**
 * Helper class for saving images.
 */
object ImageSaver {
    /**
     * Saves the given bitmap to the provided [saveDirectory].
     *
     * @param bitmap            Bitmap to save.
     * @param contentResolver   Resolver for creating the outputstream.
     * @param saveDirectory     Directory selected by user for saving.
     * @param format            Format to save the bitmap in.
     */
    fun saveBitmap(
        bitmap: Bitmap,
        contentResolver: ContentResolver,
        saveDirectory: DocumentFile,
        format: ImageFileFormat
    ): Uri? {
        val fileName = createUniqueFileName().toString()
        val createdFile = saveDirectory.createFile(format.mimeType, fileName) ?: return null

        if (format == ImageFileFormat.BMP) {
            val byteArray = BitmapToBMPConverter().convert(bitmap)
            contentResolver.openOutputStream(createdFile.uri).use { stream ->
                stream!!.write(byteArray)
            }
        } else {
            contentResolver.openOutputStream(createdFile.uri)?.use { stream ->
                bitmap.compress(format.compressFormat!!, 100, stream)
            }
        }
        return createdFile.uri
    }

    /**
     * Creates a 'unique' name based on the current epoch time in milliseconds.
     */
    private fun createUniqueFileName() = System.currentTimeMillis()
}