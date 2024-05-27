package com.santiagolozada.meliapp.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.santiagolozada.domain.models.DetailProductModel
import com.santiagolozada.domain.network.Result
import com.santiagolozada.domain.usecase.ProductUseCase
import com.santiagolozada.meliapp.search.SEARCH_PRODUCTS
import com.santiagolozada.meliapp.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

const val DETAIL_PRODUCT = "detail_product"

@HiltViewModel
class DetailProductViewModel @Inject constructor(
    private val productUseCase: ProductUseCase,
) : ViewModel() {

    private val _loadingState = MutableStateFlow(false)
    val loadingState: StateFlow<Boolean> = _loadingState

    private val _uiState =
        MutableStateFlow<UiState>(UiState.Success(SEARCH_PRODUCTS, emptyList<DetailProductModel>()))
    val uiState: StateFlow<UiState> = _uiState

    fun fetchProduct(idProduct: String) {
        viewModelScope.launch {
            _loadingState.value = true
            productUseCase.getDetailProduct(idProduct).collectLatest { result ->
                when (result) {
                    is Result.Success -> {
                        _uiState.value = UiState.Success(DETAIL_PRODUCT, result.data)
                    }

                    is Result.Error -> {
                        _uiState.value =
                            UiState.Error(result.exception.message ?: "An error occurred")
                    }
                }
            }
            _loadingState.value = false
        }
    }

}