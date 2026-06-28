package com.aero.techstore.ui.features.editCategory

data class EditCategoryUiState(
    val categoryId: Int = 0,
    val name: String = "",
    val description: String = "",
    val isLoading: Boolean = false,
    val isSaving: Boolean = false,
    val error: String? = null,
    val isSuccess: Boolean = false
)
