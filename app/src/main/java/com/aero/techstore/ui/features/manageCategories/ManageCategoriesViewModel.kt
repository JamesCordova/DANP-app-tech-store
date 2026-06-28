package com.aero.techstore.ui.features.manageCategories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aero.techstore.domain.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ManageCategoriesViewModel @Inject constructor(
    private val productRepository: ProductRepository
) : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    private val _error = MutableStateFlow<String?>(null)

    val uiState: StateFlow<ManageCategoriesUiState> = combine(
        productRepository.categories,
        _isLoading,
        _error
    ) { categories, loading, error ->
        ManageCategoriesUiState(
            categories = categories,
            isLoading = loading,
            error = error
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = ManageCategoriesUiState()
    )

    fun deleteCategory(categoryId: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                productRepository.deleteCategory(categoryId)
                _error.value = null
            } catch (e: Exception) {
                _error.value = e.message ?: "Error al eliminar la categoría"
            } finally {
                _isLoading.value = false
            }
        }
    }
}
