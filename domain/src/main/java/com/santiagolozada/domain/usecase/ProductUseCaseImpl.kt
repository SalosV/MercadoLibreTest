package com.santiagolozada.domain.usecase

import com.santiagolozada.domain.models.DetailProductModel
import com.santiagolozada.domain.models.ProductModel
import com.santiagolozada.domain.network.Result
import com.santiagolozada.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import org.jetbrains.annotations.VisibleForTesting

@VisibleForTesting
class ProductUseCaseImpl(private val productRepository: ProductRepository) : ProductUseCase {

    override suspend fun searchProducts(query: String): Flow<Result<List<ProductModel>>> {
        return productRepository.searchProducts(query)
    }

    override suspend fun getDetailProduct(idProduct: String): Flow<Result<DetailProductModel>> {
        return productRepository.getProductDetail(idProduct)
    }
}