package com.santiagolozada.domain.usecase

import com.santiagolozada.domain.models.DetailProductModel
import com.santiagolozada.domain.models.ProductModel
import com.santiagolozada.domain.network.Result
import kotlinx.coroutines.flow.Flow

interface ProductUseCase {

    suspend fun searchProducts(query: String): Flow<Result<List<ProductModel>>>

    suspend fun getDetailProduct(idProduct: String): Flow<Result<DetailProductModel>>
}