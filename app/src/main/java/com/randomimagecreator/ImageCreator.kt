package com.randomimagecreator

import android.content.ContentResolver
import android.graphics.Bitmap
import android.graphics.Color
import android.net.Uri
import androidx.documentfile.provider.DocumentFile
import com.randomimagecreator.algorithms.ImageCreating
import com.randomimagecreator.algorithms.Mandelbrot
import com.randomimagecreator.algorithms.Pixelated
import com.randomimagecreator.algorithms.SierpinskiCarpet
import com.randomimagecreator.algorithms.SolidColor
import com.randomimagecreator.common.HsvToHexConverter
import com.randomimagecreator.common.ImageSaver
import com.randomimagecreator.configuration.Configuration
import com.randomimagecreator.configuration.ImagePattern
import com.randomimagecreator.result.ImageCreationResult
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlin.system.measureTimeMillis

class ImageCreator {
    var bitmapSaveNotifier: MutableSharedFlow<Nothing?> = MutableSharedFlow()
    suspend fun create(
        contentResolver: ContentResolver,
        saveDirectory: DocumentFile,
        configuration: Configuration
    ): ImageCreationResult {
        val width = configuration.width
        val height = configuration.height
        val algorithm: ImageCreating = when (configuration.pattern) {
            ImagePattern.SOLID -> SolidColor(width, height)
            ImagePattern.PIXELATED -> Pixelated(width, height)
            ImagePattern.SIERPINSKI_CARPET -> SierpinskiCarpet(width, height)
            ImagePattern.MANDELBROT ->
                Mandelbrot(width, height, configuration.iterations, HsvToHexConverter)
        }

        val images = mutableListOf<Array<String>>()
        val creationDurationInMilliseconds = measureTimeMillis {
            for (i in 0..<configuration.amount) {
                val image = algorithm.createImage()
                val flattenedImage = image.flatMap { it.asIterable() }.toTypedArray()
                images.add(flattenedImage)
            }
        }

        val bitmaps = images.map { bitmap ->
            Bitmap.createBitmap(
                bitmap.map { pixel -> Color.parseColor(pixel) }.toIntArray(),
                configuration.width,
                configuration.height,
                Bitmap.Config.ARGB_8888
            ).also {
                it.setHasAlpha(false)
            }
        }

        val uris = mutableListOf<Uri>()
        val saveDurationInMilliseconds = measureTimeMillis {
            for (bitmap in bitmaps) {
                bitmapSaveNotifier.emit(null)
                val uri = ImageSaver.saveBitmap(
                    bitmap,
                    contentResolver,
                    saveDirectory,
                    configuration.format,
                )
                uri?.let { uris.add(it) }
            }
        }

        val durationInMilliseconds = creationDurationInMilliseconds + saveDurationInMilliseconds
        return ImageCreationResult(uris, durationInMilliseconds)
    }
}
