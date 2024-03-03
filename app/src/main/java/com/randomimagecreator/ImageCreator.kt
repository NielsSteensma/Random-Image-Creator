package com.randomimagecreator

import android.content.ContentResolver
import android.net.Uri
import androidx.documentfile.provider.DocumentFile
import com.randomimagecreator.algorithms.ImageCreating
import com.randomimagecreator.algorithms.Mandelbrot
import com.randomimagecreator.algorithms.Pixelated
import com.randomimagecreator.algorithms.SierpinskiCarpet
import com.randomimagecreator.algorithms.SolidColor
import com.randomimagecreator.common.BitmapCreator
import com.randomimagecreator.common.HsvToHexConverter
import com.randomimagecreator.common.ImageSaver
import com.randomimagecreator.configuration.Configuration
import com.randomimagecreator.configuration.ImagePattern
import com.randomimagecreator.result.ImageCreationResult
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlin.system.measureTimeMillis

class ImageCreator(
    private val imageSaver: ImageSaver = ImageSaver(),
    private val bitmapCreator: BitmapCreator = BitmapCreator()
) {
    val bitmapSafeNotifier get() = _bitmapSaveNotifier.asSharedFlow()
    private var _bitmapSaveNotifier: MutableSharedFlow<Nothing?> = MutableSharedFlow()

    suspend fun create(
        contentResolver: ContentResolver,
        saveDirectory: DocumentFile,
        configuration: Configuration
    ): Result<ImageCreationResult> {
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
                val image = try {
                    algorithm.createImage()
                } catch (exception: Exception) {
                    return Result.failure(ImageCreatingAlgorithmError(exception))
                }
                val flattenedImage = image.flatMap { it.asIterable() }.toTypedArray()
                images.add(flattenedImage)
            }
        }

        val bitmaps = try {
            images.map { bitmap ->
                bitmapCreator.create(bitmap, configuration.width, configuration.height)
            }
        } catch (exception: IllegalArgumentException) {
            return Result.failure(BitmapCreationError(exception))
        }

        val uris = mutableListOf<Uri>()
        val saveDurationInMilliseconds = measureTimeMillis {
            for (bitmap in bitmaps) {
                val uri = try {
                    imageSaver.saveBitmap(
                        bitmap,
                        contentResolver,
                        saveDirectory,
                        configuration.format,
                    )
                } catch (exception: Exception) {
                    return Result.failure(BitmapSaveError(exception))
                }
                _bitmapSaveNotifier.emit(null)
                uri?.let { uris.add(it) }
            }
        }

        val durationInMilliseconds = creationDurationInMilliseconds + saveDurationInMilliseconds
        return Result.success(ImageCreationResult(uris, durationInMilliseconds))
    }
}

class ImageCreatingAlgorithmError(cause: Throwable) : Error(cause)
class BitmapCreationError(cause: Throwable) : Error(cause)
class BitmapSaveError(cause: Throwable) : Error(cause)