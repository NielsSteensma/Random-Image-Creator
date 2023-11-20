package com.randomimagecreator.result

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ImagesGrid(imageUris: List<Uri>) {
    val imageLoader = ImageLoader(LocalContext.current)

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
    ) {
        items(imageUris.size) { index ->
            val uri = imageUris[index]
            val bitmap = imageLoader.loadImageBitmap(uri)
            Image(
                bitmap = bitmap.asImageBitmap(),
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .padding(index),
                contentDescription = "Created image ${index + 1}",
            )
        }
    }
}

private fun Modifier.padding(index: Int): Modifier {
    val isLeftColumn = index % 2 == 0
    return if (isLeftColumn) {
        padding(end = 4.dp, bottom = 8.dp)
    } else {
        padding(start = 4.dp, bottom = 8.dp)
    }
}

@Preview(showSystemUi = true)
@Composable
fun ImagesGridPreview() {
    ImagesGrid(imageUris = mutableListOf())
}