package com.santiagolozada.meliapp.search

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.santiagolozada.domain.usecase.ProductUseCase
import com.santiagolozada.domain.models.ProductModel
import com.santiagolozada.domain.network.Result
import com.santiagolozada.meliapp.utils.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.*
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class SearchViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var productUseCase: ProductUseCase

    private lateinit var searchViewModel: SearchViewModel

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)
        searchViewModel = SearchViewModel(productUseCase)
    }

    @Test
    fun `searchProducts success updates uiState with products`() = runTest {
        val query = "test"
        val products = listOf(
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
        val resultFlow = flow {
            emit(Result.Success(products))
        }
        `when`(productUseCase.searchProducts(query)).thenReturn(resultFlow)

        searchViewModel.searchProducts(query)

        // Avanzar en el tiempo para que las coroutines se completen
        advanceUntilIdle()

        // Verificar el estado de carga
        Assert.assertFalse(searchViewModel.loadingState.value)

        // Verificar el estado de éxito
        Assert.assertTrue(searchViewModel.uiState.value is UiState.Success)
        val successState = searchViewModel.uiState.value as UiState.Success
        Assert.assertEquals(SEARCH_PRODUCTS, successState.flow)
        Assert.assertEquals(products, successState.data)
    }

    @Test
    fun `searchProducts error updates uiState with error`() = runTest {
        val query = "test"
        val errorMessage = "An error occurred"
        val resultFlow = flow {
            emit(Result.Error(Exception(errorMessage)))
        }
        `when`(productUseCase.searchProducts(query)).thenReturn(resultFlow)

        searchViewModel.searchProducts(query)

        // Avanzar en el tiempo para que las coroutines se completen
        advanceUntilIdle()

        // Verificar el estado de error
        Assert.assertTrue(searchViewModel.uiState.value is UiState.Error)
        val errorState = searchViewModel.uiState.value as UiState.Error
        Assert.assertEquals(errorMessage, errorState.message)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // Reset the main dispatcher to the original Main dispatcher
    }

}