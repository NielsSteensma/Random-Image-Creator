package com.randomimagecreator.common

import android.content.ContentResolver
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import androidx.documentfile.provider.DocumentFile
import com.randomimagecreator.configuration.ImageFileFormat

/**
 * Helper class for saving images.
 */
object ImageSaver {
    /**
     * Saves the given array of bitmaps to the provided [directory].
     *
     * @param bitmaps           A list of all bitmaps to save.
     * @param context           The context of the caller.
     * @param directory         Directory selected by user for saving.
     * @param format            Format to save the bitmap in.
     * @param notifier          Notifier that emits the amount of saved bitmaps.
     */
    fun saveBitmaps(
        bitmaps: List<Bitmap>,
        context: Context,
        directory: Uri,
        format: ImageFileFormat,
//        notifier: MutableSharedFlow<Nothing?>
    ): List<Uri> {
        val rootDocumentFile = DocumentFile.fromTreeUri(context, directory)
        assert(rootDocumentFile != null) { "root document file shouldn't be null" }

        val bitmapUris = mutableListOf<Uri>()

        for (bitmap in bitmaps) {
            saveBitmap(bitmap, context.contentResolver, rootDocumentFile!!, format)?.let {
                bitmapUris.add(it)
//                scope.launch {
//                    notifier.emit(null)
//                }
            }
        }
        return bitmapUris
    }

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