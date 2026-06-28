package com.aero.techstore.ui.features.favorites

import com.aero.techstore.domain.model.Product

data class FavoritesUiState(
    val favoriteProducts: List<Product> = emptyList(),
    val favoriteProductIds: Set<Int> = emptySet()
)
