package com.randomimagecreator.result

import android.net.Uri

data class ImageCreationResult(val uris: List<Uri>, val durationInMilliseconds: Long)