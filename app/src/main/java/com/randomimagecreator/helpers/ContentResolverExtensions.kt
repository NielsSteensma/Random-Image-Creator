package com.randomimagecreator.helpers

import android.content.ContentResolver
import android.database.Cursor
import android.net.Uri

fun ContentResolver.query(uri: Uri): Cursor? {
    return query(uri,null,null,null,null)
}
