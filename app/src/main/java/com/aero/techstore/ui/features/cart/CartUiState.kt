package com.aero.techstore.ui.features.cart

import com.aero.techstore.domain.model.Cart

data class CartUiState(
    val cart: Cart = Cart()
)
