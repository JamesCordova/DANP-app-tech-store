package com.aero.techstore.ui.features.addProduct

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aero.techstore.domain.model.ProductCategory
import com.aero.techstore.domain.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddProductViewModel @Inject constructor(
    private val productRepository: ProductRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(AddProductUiState())
    val uiState: StateFlow<AddProductUiState> = _uiState.asStateFlow()

    init {
        productRepository.categories
            .onEach { categories ->
                _uiState.update { it.copy(categories = categories) }
            }
            .launchIn(viewModelScope)
    }

    fun onNameChange(name: String) {
        _uiState.update { it.copy(name = name) }
    }

    fun onDescriptionChange(description: String) {
        _uiState.update { it.copy(description = description) }
    }

    fun onPriceChange(price: String) {
        _uiState.update { it.copy(price = price) }
    }

    fun onImageUrlChange(imageUrl: String) {
        _uiState.update { it.copy(imageUrl = imageUrl) }
    }

    fun onCategorySelected(category: ProductCategory) {
        _uiState.update { it.copy(selectedCategory = category) }
    }

    fun onAddProduct() {
        val state = _uiState.value
        
        if (state.name.isBlank() || state.price.isBlank() || state.selectedCategory == null) {
            _uiState.update { it.copy(error = "Por favor completa los campos obligatorios") }
            return
        }

        val priceDouble = state.price.toDoubleOrNull()
        if (priceDouble == null) {
            _uiState.update { it.copy(error = "Precio inválido") }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                productRepository.addProduct(
                    name = state.name,
                    description = state.description,
                    price = priceDouble,
                    categoryId = state.selectedCategory.id,
                    imageUrl = state.imageUrl
                )
                _uiState.update { it.copy(isLoading = false, isSuccess = true) }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, error = e.message ?: "Error desconocido") }
            }
        }
    }

    fun resetSuccess() {
        _uiState.update { it.copy(isSuccess = false) }
    }
}
