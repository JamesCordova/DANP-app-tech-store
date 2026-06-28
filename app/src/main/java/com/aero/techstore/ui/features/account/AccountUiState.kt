package com.aero.techstore.ui.features.account

data class AccountUiState(
    val userName: String = "",
    val userEmail: String = "",
    val isLoading: Boolean = false
)
