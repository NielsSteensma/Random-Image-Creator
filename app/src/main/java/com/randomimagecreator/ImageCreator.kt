package com.randomimagecreator

import algorithms.ImageCreating
import algorithms.Mandelbrot
import algorithms.Pixelated
import algorithms.SierpinskiCarpet
import algorithms.SolidColor
import android.content.ContentResolver
import android.graphics.Bitmap
import android.graphics.Color
import android.net.Uri
import androidx.documentfile.provider.DocumentFile
import com.randomimagecreator.common.ImageSaver
import com.randomimagecreator.configuration.Configuration
import com.randomimagecreator.configuration.ImagePattern
import com.randomimagecreator.result.ImageCreationResult
import kotlin.system.measureTimeMillis

class ImageCreator {

    fun create(
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
            ImagePattern.MANDELBROT -> Mandelbrot(width, height, configuration.iterations)
        }

        val images = mutableListOf<Array<String>>()
        val creationDurationInMilliseconds = measureTimeMillis {
            for (i in 0..configuration.amount) {
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
            )
        }

        val uris = mutableListOf<Uri>()
        val saveDurationInMilliseconds = measureTimeMillis {
            for (bitmap in bitmaps) {
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
