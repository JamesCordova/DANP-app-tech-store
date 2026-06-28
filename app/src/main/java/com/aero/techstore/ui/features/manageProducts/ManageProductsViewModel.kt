package com.aero.techstore.ui.features.manageProducts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aero.techstore.domain.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ManageProductsViewModel @Inject constructor(
    private val productRepository: ProductRepository
) : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    private val _error = MutableStateFlow<String?>(null)

    val uiState: StateFlow<ManageProductsUiState> = combine(
        productRepository.products,
        _isLoading,
        _error
    ) { products, loading, error ->
        ManageProductsUiState(
            products = products,
            isLoading = loading,
            error = error
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = ManageProductsUiState()
    )

    fun deleteProduct(productId: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                productRepository.deleteProduct(productId)
                _error.value = null
            } catch (e: Exception) {
                _error.value = e.message ?: "Error al eliminar el producto"
            } finally {
                _isLoading.value = false
            }
        }
    }
}
