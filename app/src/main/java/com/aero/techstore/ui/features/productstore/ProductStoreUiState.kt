package com.aero.techstore.ui.features.productstore

import com.aero.techstore.domain.model.Product
import com.aero.techstore.domain.model.User

data class ProductStoreUiState(
    val products: List<Product> = emptyList(),
    val categories: List<String> = emptyList(),
    val selectedCategory: String = "Todos",
    val favoriteProductIds: Set<Int> = emptySet(),
    val searchQuery: String = "",
    val currentUser: User = User(
        id = 1,
        name = "Usuario Demo",
        email = "usuario@example.com",
        favoriteProductIds = emptyList()
    )
) {
    val filteredProducts: List<Product>
        get() {
            val categoryFiltered = if (selectedCategory == "Todos") {
                products
            } else {
                products.filter { it.category.label == selectedCategory }
            }

            return if (searchQuery.isBlank()) {
                categoryFiltered
            } else {
                categoryFiltered.filter {
                    it.name.contains(searchQuery, ignoreCase = true) ||
                            it.description.contains(searchQuery, ignoreCase = true)
                }
            }
        }
}
