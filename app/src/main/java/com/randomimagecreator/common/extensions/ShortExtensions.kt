package com.randomimagecreator.common.extensions

import java.nio.ByteBuffer
import java.nio.ByteOrder

/**
 * Converts the current value to a [ByteArray] with an order of Little Endian.
 */
fun Short.toLittleEndian(): ByteArray =
    ByteBuffer.allocate(2).apply {
        order(ByteOrder.LITTLE_ENDIAN)
        putShort(this@toLittleEndian)
    }.array()
