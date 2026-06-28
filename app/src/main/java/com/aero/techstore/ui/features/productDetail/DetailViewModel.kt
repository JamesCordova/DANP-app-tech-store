package com.aero.techstore.ui.features.productDetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aero.techstore.domain.repository.CartRepository
import com.aero.techstore.domain.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    
    val productId: Int = savedStateHandle["productId"] ?: 0

    val uiState: StateFlow<DetailUiState> = combine(
        productRepository.products.map { products -> products.find { it.id == productId } },
        productRepository.favoriteProductIds.map { favorites -> favorites.contains(productId) }
    ) { product, isFavorite ->
        DetailUiState(product, isFavorite)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = DetailUiState()
    )

    fun toggleFavorite() {
        productRepository.toggleFavorite(productId)
    }

    fun addToCart() {
        uiState.value.product?.let {
            cartRepository.addToCart(it)
        }
    }
}
