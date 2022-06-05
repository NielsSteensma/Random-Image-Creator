package com.randomimagecreator.helpers

import android.graphics.Bitmap
import java.nio.ByteBuffer

/**
 * The amount of bytes each 24-bit pixel has.
 */
private const val BYTE_PER_PIXEL = 3

/**
 * Constraint of BMP format that enforces each pixel array to
 * start at a memory address that is dividable by 4.
 */
private const val BMP_CONSTRAINT = 4

/**
 * Size in bytes of the BMP file header.
 */
private const val FILE_HEADER_SIZE_BYTES = 14

/**
 * Size in bytes of the DIB header.
 */
private const val DIB_HEADER_SIZE_BYTES = 40

/**
 * Converts a [Bitmap] to a BMP file format.
 */
class BitmapToBMPConverter {

    /**
     * Converts the [bitmap] to a [ByteArray] of BMP format.
     */
    fun convert(bitmap: Bitmap): ByteArray {
        val imageSizeBytes = calculateImageSize(bitmap)
        val fileSizeBytes = calculateFileSize(imageSizeBytes)
        val buffer = ByteBuffer.allocate(fileSizeBytes)
        writeFileHeader(buffer, fileSizeBytes)
        writeDIBHeader(buffer, bitmap, imageSizeBytes)
        writeImageData(buffer, bitmap)
        return buffer.array()
    }

    /**
     * Returns a [Boolean] indicating if the BMP requires padding at the end. This is the case when
     * the bitmap can't be divided by 4.
     *
     * BMP requires bitmap lines to be dividable by 4 so operations on the BMP can be faster.
     */
    private fun requiresPadding(bitmap: Bitmap) = bitmap.width % BMP_CONSTRAINT != 0

    /**
     * Returns an [Int] with the amount of dummy bytes to append at the end of each row as padding.
     */
    private fun calculateDummyBytes(bitmap: Bitmap) =
        (BMP_CONSTRAINT - (bitmap.width % BMP_CONSTRAINT))

    /**
     * Returns an [Int] with the amount of bytes for storing the image data.
     */
    private fun calculateImageSize(bitmap: Bitmap): Int {
        val dummyBytes = if (requiresPadding(bitmap)) calculateDummyBytes(bitmap) else 0
        val widthBytes = (bitmap.width + dummyBytes) * BYTE_PER_PIXEL
        return widthBytes * bitmap.height
    }

    /**
     * Returns an [Int] with the amount of bytes for storing the image and header data.
     */
    private fun calculateFileSize(imageSizeBytes: Int) =
        FILE_HEADER_SIZE_BYTES + DIB_HEADER_SIZE_BYTES + imageSizeBytes

    /**
     * Writes the file header to the [ByteBuffer].
     */
    private fun writeFileHeader(buffer: ByteBuffer, fileSize: Int) {
        buffer.apply {
            // Id field "BM"
            put(0x42)
            put(0x4d)

            // Size of BMP file
            put(fileSize.toLittleEndian())

            // Unused
            buffer.put(0.toShort().toLittleEndian())
            buffer.put(0.toShort().toLittleEndian())

            // Offset where actual pixel data can be found
            put(0x36.toLittleEndian())
        }
    }

    /**
     * Writes the DIB header to the [ByteBuffer].
     */
    private fun writeDIBHeader(buffer: ByteBuffer, bitmap: Bitmap, imageSizeBytes: Int) {
        buffer.apply {
            // Number of bytes in the header
            put(0x28.toLittleEndian())

            // Width of BMP
            put(bitmap.width.toLittleEndian())

            // Height of BMP
            put(bitmap.height.toLittleEndian())

            // Number of color planes
            put(1.toShort().toLittleEndian())

            // Number of bits per pixel
            put(24.toShort().toLittleEndian())

            // Pixel compression used
            put(0.toLittleEndian())

            // Size of image
            put(imageSizeBytes.toLittleEndian())

            // Horizontal resolution in px
            put(0.toLittleEndian())

            // Vertical resolution in px
            put(0.toLittleEndian())

            // Number of colors in pallet
            put(0.toLittleEndian())

            // Number of important colors ( 0 all are important )
            put(0.toLittleEndian())
        }
    }

    /**
     * Writes the image data to the [ByteBuffer] and, if necessarily, adds
     * the required dummy bytes for each row.
     */
    private fun writeImageData(buffer: ByteBuffer, bitmap: Bitmap) {
        val column = bitmap.width
        var row = bitmap.height
        val pixels = IntArray(bitmap.width * bitmap.height)
        bitmap.getPixels(pixels, 0, bitmap.width, 0, 0, bitmap.width, bitmap.height)

        while (row > 0) {
            val startPosition = (row - 1) * column
            val endPosition = row * column

            for (i in startPosition until endPosition) {
                buffer.put(pixels[i].to24BitPixel())
            }

            row--
        }
    }
}

