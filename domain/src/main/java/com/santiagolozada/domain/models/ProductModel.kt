package com.santiagolozada.domain.models

data class ProductModel(
    val id: String,
    val thumbnail: String,
    val title: String,
    val price: Double,
    val condition: String,
    val availableQuantity: Int,
)
