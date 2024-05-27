package com.santiagolozada.data.models

import com.google.gson.annotations.SerializedName
import com.santiagolozada.domain.models.ProductModel

data class SearchNetwork(
    @SerializedName("results") val results: List<ProductNetwork>
)

data class ProductNetwork(
    @SerializedName("id") val id: String,
    @SerializedName("thumbnail") val thumbnail: String,
    @SerializedName("title") val title: String,
    @SerializedName("price") val price: Double,
    @SerializedName("condition") val condition: String,
    @SerializedName("available_quantity") val availableQuantity: Int
)

fun ProductNetwork.asExternalModel() = ProductModel(
    id,
    thumbnail,
    title,
    price,
    condition,
    availableQuantity
)