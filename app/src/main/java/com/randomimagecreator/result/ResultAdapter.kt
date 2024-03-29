package com.randomimagecreator.result

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.randomimagecreator.R

/**
 * Adapter for showing a list of generated images.
 */
internal class ResultAdapter(private val createdImageUris: List<Uri>) :
    RecyclerView.Adapter<ImageViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.viewholder_image, viewGroup, false)
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.onBind(createdImageUris[position])
    }

    override fun getItemCount() = createdImageUris.size
}
