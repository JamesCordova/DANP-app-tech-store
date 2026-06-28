package com.aero.techstore.ui.features.manageProducts

import com.aero.techstore.domain.model.Product

data class ManageProductsUiState(
    val products: List<Product> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
