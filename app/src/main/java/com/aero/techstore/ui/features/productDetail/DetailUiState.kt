package com.aero.techstore.ui.features.productDetail

import com.aero.techstore.domain.model.Product

data class DetailUiState(
    val product: Product? = null,
    val isFavorite: Boolean = false
)
