package com.santiagolozada.meliapp.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.santiagolozada.domain.models.ProductPicturesModel
import com.santiagolozada.meliapp.R
import kotlin.properties.Delegates

class ImageCarouselAdapter : RecyclerView.Adapter<ImageCarouselAdapter.ImageViewHolder>() {

    var imageUrls: List<ProductPicturesModel> by Delegates.observable(
        emptyList()
    ) { _, _, _ ->
        notifyDataSetChanged()
    }

    class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.image_view, parent, false)
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val imageUrl = imageUrls[position]
        holder.imageView.loadUrl(imageUrl.url)
    }

    override fun getItemCount(): Int = imageUrls.size
}