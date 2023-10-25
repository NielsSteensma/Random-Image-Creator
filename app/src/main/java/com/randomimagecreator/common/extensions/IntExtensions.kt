package com.randomimagecreator.common.extensions

import java.nio.ByteBuffer
import java.nio.ByteOrder

/**
 * Converts the current value to a [ByteArray] with an order of Little Endian.
 */
fun Int.toLittleEndian(): ByteArray =
    ByteBuffer.allocate(4).apply {
        this.order(ByteOrder.LITTLE_ENDIAN)
        putInt(this@toLittleEndian)
    }.array()

/**
 * Converts the current value to a 24 bit pixel returned as a [ByteArray].
 */
fun Int.to24BitPixel() = ByteArray(3).also {
    it[0] = (this and 0x000000FF).toByte()
    it[1] = (this and 0x0000FF00 shr 8).toByte()
    it[2] = (this and 0x00FF0000 shr 16).toByte()
}
