package com.santiagolozada.domain.models

data class DetailProductModel(
    val id: String,
    val title: String,
    val price: String,
    val condition: String,
    val pictures: List<ProductPicturesModel>,
)

data class ProductPicturesModel(
    val id: String,
    val url: String,
)
