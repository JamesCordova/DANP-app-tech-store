package com.aero.techstore.ui.features.productstore

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aero.techstore.domain.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class ProductStoreViewModel @Inject constructor(
    private val productRepository: ProductRepository
) : ViewModel() {
    private val _selectedCategory = MutableStateFlow("Todos")
    private val _searchQuery = MutableStateFlow("")

    val uiState: StateFlow<ProductStoreUiState> = combine(
        productRepository.products,
        productRepository.categories,
        productRepository.favoriteProductIds,
        _selectedCategory,
        _searchQuery
    ) { products, categories, favorites, category, query ->
        ProductStoreUiState(
            products = products,
            categories = listOf("Todos") + categories.map { it.label },
            favoriteProductIds = favorites,
            selectedCategory = category,
            searchQuery = query
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = ProductStoreUiState()
    )

	fun onCategorySelected(category: String) {
		_selectedCategory.value = category
	}

	fun onSearchQueryChanged(query: String) {
		_searchQuery.value = query
	}

	fun toggleFavorite(productId: Int) {
		productRepository.toggleFavorite(productId)
	}

	fun isFavorite(productId: Int): Boolean {
		return uiState.value.favoriteProductIds.contains(productId)
	}

	fun getFavoriteCount(): Int {
		return uiState.value.favoriteProductIds.size
	}
}
