package com.randomimagecreator.common.errors

import android.os.Build

class SaveDirectoryMissingError :
    Error("Unable to create save directory, Android version:" + Build.VERSION.SDK_INT)