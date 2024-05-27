package com.santiagolozada.data.repository

import com.santiagolozada.data.models.ProductNetwork
import com.santiagolozada.data.models.SearchNetwork
import com.santiagolozada.data.service.ApiService
import com.santiagolozada.domain.models.ProductModel
import com.santiagolozada.domain.network.NetworkException
import com.santiagolozada.domain.network.Result.*
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import okhttp3.ResponseBody
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import retrofit2.HttpException
import retrofit2.Response

@ExperimentalCoroutinesApi
class ProductRepositoryImplTest {

    @Mock
    private lateinit var apiService: ApiService

    private lateinit var productRepository: ProductRepositoryImpl

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var searchNetwork: SearchNetwork

    private lateinit var productModel: List<ProductModel>

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        productRepository = ProductRepositoryImpl(apiService, testDispatcher)
        searchNetwork = SearchNetwork(
            listOf(
                ProductNetwork(
                    "1",
                    "http://http2.mlstatic.com/D_882009-MLA52254595871_112022-I.jpg",
                    "Product 1",
                    2400.0,
                    "new",
                    2
                ),
                ProductNetwork(
                    "2",
                    "http://http2.mlstatic.com/D_882009-MLA52254595871_112022-I.jpg",
                    "Product 2",
                    3400.0,
                    "new",
                    2
                ),
            )
        )

        productModel = listOf(
            ProductModel(
                "1",
                "http://http2.mlstatic.com/D_882009-MLA52254595871_112022-I.jpg",
                "Product 1",
                2400.0,
                "new",
                2
            ),
            ProductModel(
                "2",
                "http://http2.mlstatic.com/D_882009-MLA52254595871_112022-I.jpg",
                "Product 2",
                3400.0,
                "new",
                2
            ),
        )
        Dispatchers.setMain(testDispatcher)
    }

    @Test
    fun `searchProducts success returns products`() = runTest {
        val query = "test"
        val limit = 10
        `when`(apiService.getItems(query, limit)).thenReturn(searchNetwork)

        val flow = productRepository.searchProducts(query, limit)

        flow.collect { result ->
            Assert.assertTrue(result is Success)
            val successResult = result as Success
            assertEquals(productModel, successResult.data)
        }
    }

    @Test
    fun `searchProducts http error returns error`() = runTest {
        val query = "test"
        val limit = 10
        val httpException = HttpException(Response.error<Any>(404, ResponseBody.create(null, "")))
        `when`(apiService.getItems(query, limit)).thenThrow(httpException)

        val flow = productRepository.searchProducts(query, limit)

        flow.collect { result ->
            Assert.assertTrue(result is Error)
            val errorResult = result as Error
            Assert.assertTrue(errorResult.exception is NetworkException.NotFoundException)
        }
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // Reset the main dispatcher to the original Main dispatcher
    }
}