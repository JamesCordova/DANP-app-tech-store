package com.aero.techstore.ui.features.addProduct

import com.aero.techstore.domain.model.ProductCategory

data class AddProductUiState(
    val name: String = "",
    val description: String = "",
    val price: String = "",
    val imageUrl: String = "",
    val categories: List<ProductCategory> = emptyList(),
    val selectedCategory: ProductCategory? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
    val isSuccess: Boolean = false
)
