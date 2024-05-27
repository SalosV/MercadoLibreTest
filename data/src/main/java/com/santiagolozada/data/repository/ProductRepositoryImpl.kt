package com.santiagolozada.data.repository

import android.util.Log
import com.santiagolozada.data.models.asExternalModel
import com.santiagolozada.domain.network.NetworkException
import com.santiagolozada.data.service.ApiService
import com.santiagolozada.domain.network.Result
import com.santiagolozada.domain.repository.ProductRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val ioDispatcher: CoroutineDispatcher
) : ProductRepository {

    override suspend fun searchProducts(query: String, limit: Int) = flow {
        try {
            val result = apiService.getItems(query, limit).results
            emit(Result.Success(result.map { it.asExternalModel() }))
        } catch (exception: HttpException) {
            emit(handleErrorResult(exception))
        } catch (exception: Exception) {
            Log.e("searchProducts", exception.message.toString())
            emit(Result.Error(NetworkException.UnknownNetworkException("Unknown Error")))
        }
    }.flowOn(ioDispatcher)

    override suspend fun getProductDetail(idProduct: String) = flow {
        try {
            val result = apiService.getProductDetail(idProduct)
            emit(Result.Success(result.asExternalModel()))
        } catch (exception: HttpException) {
            Log.e("productDetail", exception.message.toString())
            emit(handleErrorResult(exception))
        }
    }.flowOn(ioDispatcher)

    private fun handleErrorResult(exception: HttpException): Result.Error {
        val networkException = when (exception.code()) {
            400 -> NetworkException.BadRequestException("Bad Request")
            401 -> NetworkException.UnauthorizedException("Unauthorized")
            403 -> NetworkException.ForbiddenException("Forbidden")
            404 -> NetworkException.NotFoundException("Not Found")
            500 -> NetworkException.InternalServerErrorException("Internal Server Error")
            else -> NetworkException.UnknownNetworkException("Unknown Error")
        }

        return Result.Error(networkException)
    }

}