package com.randomimagecreator.result

import android.net.Uri
import com.randomimagecreator.configuration.ImagePattern

data class Result(
    val amount: Int,
    val pattern: ImagePattern,
    val directoryName: String,
    val durationInMillis: Long,
    val uris: List<Uri>
)