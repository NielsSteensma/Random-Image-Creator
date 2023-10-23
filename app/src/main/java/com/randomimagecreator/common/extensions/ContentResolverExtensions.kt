package com.randomimagecreator.common.extensions

import android.content.ContentResolver
import android.database.Cursor
import android.net.Uri

fun ContentResolver.query(uri: Uri): Cursor? {
    return query(uri,null,null,null,null)
}
