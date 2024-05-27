package com.santiagolozada.meliapp.utils

sealed class UiState {
    data class Success(val flow: String, val data: Any) : UiState()
    data class Error(val message: String) : UiState()
}