package com.aero.techstore.ui.features.addCategory

data class AddCategoryUiState(
    val name: String = "",
    val description: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val isSuccess: Boolean = false
)
