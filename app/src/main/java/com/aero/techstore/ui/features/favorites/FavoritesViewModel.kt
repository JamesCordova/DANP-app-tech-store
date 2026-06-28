package com.aero.techstore.ui.features.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aero.techstore.domain.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val productRepository: ProductRepository
) : ViewModel() {
    val uiState: StateFlow<FavoritesUiState> = combine(
        productRepository.products,
        productRepository.favoriteProductIds
    ) { products, favorites ->
        FavoritesUiState(
            favoriteProducts = products.filter { it.id in favorites },
            favoriteProductIds = favorites
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = FavoritesUiState()
    )

    fun toggleFavorite(productId: Int) {
        productRepository.toggleFavorite(productId)
    }

    fun isFavorite(productId: Int): Boolean {
        return uiState.value.favoriteProductIds.contains(productId)
    }
}
