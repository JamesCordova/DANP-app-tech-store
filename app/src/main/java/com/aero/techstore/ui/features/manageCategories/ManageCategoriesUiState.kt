package com.aero.techstore.ui.features.manageCategories

import com.aero.techstore.domain.model.ProductCategory

data class ManageCategoriesUiState(
    val categories: List<ProductCategory> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
