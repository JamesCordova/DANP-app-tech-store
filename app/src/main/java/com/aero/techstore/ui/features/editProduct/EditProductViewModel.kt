package com.aero.techstore.ui.features.editProduct

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aero.techstore.domain.model.ProductCategory
import com.aero.techstore.domain.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditProductViewModel @Inject constructor(
    private val productRepository: ProductRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val productId: Int = checkNotNull(savedStateHandle["productId"])

    private val _uiState = MutableStateFlow(EditProductUiState(productId = productId))
    val uiState: StateFlow<EditProductUiState> = _uiState.asStateFlow()

    init {
        loadProductData()
        
        productRepository.categories
            .onEach { categories ->
                _uiState.update { it.copy(categories = categories) }
            }
            .launchIn(viewModelScope)
    }

    private fun loadProductData() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            
            // Wait for products to be loaded in repository if necessary, 
            // but since it's a StateFlow we can just find it.
            val product = productRepository.products.value.find { it.id == productId }
            
            if (product != null) {
                _uiState.update { it.copy(
                    name = product.name,
                    description = product.description,
                    price = product.price.toString(),
                    imageUrl = product.imageUrl,
                    selectedCategory = product.category,
                    isLoading = false
                ) }
            } else {
                _uiState.update { it.copy(isLoading = false, error = "Producto no encontrado") }
            }
        }
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

    fun onUpdateProduct() {
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
            _uiState.update { it.copy(isSaving = true, error = null) }
            try {
                productRepository.updateProduct(
                    productId = state.productId,
                    name = state.name,
                    description = state.description,
                    price = priceDouble,
                    categoryId = state.selectedCategory.id,
                    imageUrl = state.imageUrl
                )
                _uiState.update { it.copy(isSaving = false, isSuccess = true) }
            } catch (e: Exception) {
                _uiState.update { it.copy(isSaving = false, error = e.message ?: "Error desconocido") }
            }
        }
    }

    fun resetSuccess() {
        _uiState.update { it.copy(isSuccess = false) }
    }
}
