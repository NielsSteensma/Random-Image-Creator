package com.randomimagecreator.result

object ImageCreationDurationFormatter {

    fun seconds(milliseconds: Long) =
        milliseconds / 1000.0
}