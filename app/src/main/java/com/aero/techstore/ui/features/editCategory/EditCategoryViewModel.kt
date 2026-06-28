package com.aero.techstore.ui.features.editCategory

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aero.techstore.domain.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditCategoryViewModel @Inject constructor(
    private val productRepository: ProductRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val categoryId: Int = checkNotNull(savedStateHandle["categoryId"])

    private val _uiState = MutableStateFlow(EditCategoryUiState(categoryId = categoryId))
    val uiState: StateFlow<EditCategoryUiState> = _uiState.asStateFlow()

    init {
        loadCategoryData()
    }

    private fun loadCategoryData() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            
            val category = productRepository.categories.value.find { it.id == categoryId }
            
            if (category != null) {
                _uiState.update { it.copy(
                    name = category.label,
                    description = "", // We might need to fetch the full category DTO if description is needed
                    isLoading = false
                ) }
            } else {
                _uiState.update { it.copy(isLoading = false, error = "Categoría no encontrada") }
            }
        }
    }

    fun onNameChange(name: String) {
        _uiState.update { it.copy(name = name) }
    }

    fun onDescriptionChange(description: String) {
        _uiState.update { it.copy(description = description) }
    }

    fun onUpdateCategory() {
        val state = _uiState.value
        if (state.name.isBlank()) {
            _uiState.update { it.copy(error = "El nombre es obligatorio") }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isSaving = true, error = null) }
            try {
                productRepository.updateCategory(
                    categoryId = state.categoryId,
                    name = state.name,
                    description = state.description.ifBlank { null }
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
