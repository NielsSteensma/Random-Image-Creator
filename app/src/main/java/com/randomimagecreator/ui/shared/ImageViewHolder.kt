package com.randomimagecreator.ui.shared

import android.net.Uri
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.randomimagecreator.R

/**
 * Displays a single image with a surrounding margin of 5dp.
 */
class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun onBind(imageUri: Uri) {
        val imageView = itemView.findViewById<ImageView>(R.id.imageview_viewholder_image)
        imageView.setImageURI(imageUri)
    }
}
