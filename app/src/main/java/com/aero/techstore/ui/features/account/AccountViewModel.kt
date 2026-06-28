package com.aero.techstore.ui.features.account

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow(
        AccountUiState(
            userName = "Usuario Demo",
            userEmail = "usuario@ejemplo.com"
        )
    )
    val uiState: StateFlow<AccountUiState> = _uiState.asStateFlow()

    fun onSellProductClick() {
        // Logica para vender producto
    }

    fun onAddCategoryClick() {
        // Logica para añadir categoria
    }
}
