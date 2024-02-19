package com.randomimagecreator.common.errors

import android.os.Build

class SaveDirectoryMissingError :
    Error("Unable to create savedirectory, Android version:" + Build.VERSION.SDK_INT)