package com.aero.techstore.domain.repository

import com.aero.techstore.domain.model.Product
import com.aero.techstore.domain.model.ProductCategory
import kotlinx.coroutines.flow.StateFlow

interface ProductRepository {
    val products: StateFlow<List<Product>>
    val categories: StateFlow<List<ProductCategory>>
    val favoriteProductIds: StateFlow<Set<Int>>
    fun toggleFavorite(productId: Int)
    suspend fun addCategory(name: String, description: String?)
    suspend fun updateCategory(categoryId: Int, name: String, description: String?)
    suspend fun deleteCategory(categoryId: Int)
    suspend fun addProduct(name: String, description: String, price: Double, categoryId: Int, imageUrl: String)
    suspend fun updateProduct(productId: Int, name: String, description: String, price: Double, categoryId: Int, imageUrl: String)
    suspend fun deleteProduct(productId: Int)
}
