package com.randomimagecreator.result

import java.math.BigDecimal

object DurationFormatter {

    fun seconds(milliseconds: Long): String {
        val seconds = milliseconds / 1000.0
        return BigDecimal.valueOf(seconds).stripTrailingZeros().toPlainString()
    }
}