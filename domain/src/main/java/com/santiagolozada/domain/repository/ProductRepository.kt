package com.santiagolozada.domain.repository

import LIMIT_SIZE
import com.santiagolozada.domain.models.DetailProductModel
import com.santiagolozada.domain.models.ProductModel
import com.santiagolozada.domain.network.Result
import kotlinx.coroutines.flow.Flow

interface ProductRepository {

    suspend fun searchProducts(
        query: String,
        limit: Int = LIMIT_SIZE
    ): Flow<Result<List<ProductModel>>>

    suspend fun getProductDetail(idProduct: String): Flow<Result<DetailProductModel>>

}