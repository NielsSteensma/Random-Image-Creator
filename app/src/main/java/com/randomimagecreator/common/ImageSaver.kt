package com.randomimagecreator.common

import android.content.ContentResolver
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import androidx.documentfile.provider.DocumentFile
import com.randomimagecreator.configuration.ImageFileFormat
import kotlinx.coroutines.flow.MutableStateFlow

/**
 * Helper class for saving images.
 */
object ImageSaver {
    /**
     * Saves the given array of bitmaps to the provided [directory].
     *
     * @param bitmaps           A list of all bitmaps to save.
     * @param flow              Notifier that emits the amount of saved bitmaps.
     * @param context           The context of the caller.
     * @param directory         Directory selected by user for saving.
     * @param format            Format to save the bitmap in.
     */
    suspend fun saveBitmaps(
        bitmaps: MutableList<Bitmap>,
        flow: MutableStateFlow<Int>,
        context: Context,
        directory: Uri,
        format: ImageFileFormat
    ): List<Uri> {
        val rootDocumentFile = DocumentFile.fromTreeUri(context, directory)
        assert(rootDocumentFile != null) { "root document file shouldn't be null" }

        val bitmapUris = mutableListOf<Uri>()

        var i = 0
        for (bitmap in bitmaps) {
            saveBitmap(bitmap, context.contentResolver, rootDocumentFile!!, format)?.let {
                i++
                bitmapUris.add(it)
                flow.emit(i)
            }
        }
        return bitmapUris
    }

    private fun saveBitmap(
        bitmap: Bitmap,
        contentResolver: ContentResolver,
        rootDocumentFile: DocumentFile,
        format: ImageFileFormat
    ): Uri? {
        val fileName = createUniqueFileName().toString()
        val createdFile = rootDocumentFile.createFile(format.mimeType, fileName) ?: return null

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