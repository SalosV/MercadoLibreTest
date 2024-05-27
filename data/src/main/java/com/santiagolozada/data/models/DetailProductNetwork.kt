package com.santiagolozada.data.models

import com.google.gson.annotations.SerializedName
import com.santiagolozada.domain.models.DetailProductModel
import com.santiagolozada.domain.models.ProductPicturesModel

data class DetailProductNetwork(
    @SerializedName("id") val id: String,
    @SerializedName("title") val title: String,
    @SerializedName("price") val price: String,
    @SerializedName("condition") val condition: String,
    @SerializedName("pictures") val pictures: List<ProductPicturesNetwork>,
)

data class ProductPicturesNetwork(
    @SerializedName("id") val id: String,
    @SerializedName("url") val url: String,
)

fun DetailProductNetwork.asExternalModel() = DetailProductModel(
    id = id,
    title = title,
    price = price,
    condition = condition,
    pictures = pictures.map { it.asExternalModel() }
)

fun ProductPicturesNetwork.asExternalModel() = ProductPicturesModel(
    id,
    url
)