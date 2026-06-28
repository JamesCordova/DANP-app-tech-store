package com.aero.techstore.ui.features.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aero.techstore.domain.repository.CartRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val cartRepository: CartRepository
) : ViewModel() {
    val uiState: StateFlow<CartUiState> = cartRepository.cart
        .map { CartUiState(it) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = CartUiState()
        )

    fun removeFromCart(productId: Int) {
        cartRepository.removeFromCart(productId)
    }

    fun updateQuantity(productId: Int, quantity: Int) {
        cartRepository.updateQuantity(productId, quantity)
    }

    fun clearCart() {
        cartRepository.clearCart()
    }

    fun getTotalPrice(): Double {
        return uiState.value.cart.totalPrice
    }

    fun getTotalItems(): Int {
        return uiState.value.cart.totalItems
    }
}
