package com.santiagolozada.meliapp.utils

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.santiagolozada.meliapp.R

fun ImageView.loadUrl(
    url: String,
    placeHolder: Int = R.drawable.ic_image_placeholder,
    errorImage: Int = R.drawable.ic_file_error
) {
    Glide.with(context).load(url)
        .placeholder(placeHolder)
        .error(errorImage)
        .centerCrop()
        .into(this)
}