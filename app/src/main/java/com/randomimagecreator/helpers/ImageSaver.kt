package com.randomimagecreator.helpers

import android.content.ContentResolver
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import androidx.documentfile.provider.DocumentFile
import com.randomimagecreator.common.ImageFileFormat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

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
        scope: CoroutineScope,
        bitmaps: MutableList<Bitmap>,
        context: Context,
        directory: Uri,
        format: ImageFileFormat,
        notifier: MutableSharedFlow<Nothing?>
    ): List<Uri> {
        val rootDocumentFile = DocumentFile.fromTreeUri(context, directory)
        assert(rootDocumentFile != null) { "root document file shouldn't be null" }

        val bitmapUris = mutableListOf<Uri>()

        for (bitmap in bitmaps) {
            saveBitmap(bitmap, context.contentResolver, rootDocumentFile!!, format)?.let {
                bitmapUris.add(it)
                scope.launch {
                    println("ERRROR: emit")
                    notifier.emit(null)
                }
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
            contentResolver.openOutputStream(createdFile.uri).use { stream ->
                bitmap.compress(format.compressFormat, 100, stream)
            }
        }
        return createdFile.uri
    }

    /**
     * Creates a 'unique' name based on the current epoch time in milliseconds.
     */
    private fun createUniqueFileName() = System.currentTimeMillis()
}
