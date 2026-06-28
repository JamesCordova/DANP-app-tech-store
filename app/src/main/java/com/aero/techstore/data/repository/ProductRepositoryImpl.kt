package com.aero.techstore.data.repository

import android.util.Log
import com.aero.techstore.data.remote.StoreCategoryRemoteDataSource
import com.aero.techstore.data.remote.StoreProductRemoteDataSource
import com.aero.techstore.data.remote.dto.StoreCategoryInsertDto
import com.aero.techstore.data.remote.dto.StoreCategoryUpdateDto
import com.aero.techstore.data.remote.dto.StoreProductInsertDto
import com.aero.techstore.domain.model.Product
import com.aero.techstore.domain.model.ProductCategory
import com.aero.techstore.domain.repository.ProductRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductRepositoryImpl @Inject constructor(
    private val storeProductDataSource: StoreProductRemoteDataSource,
    private val storeCategoryDataSource: StoreCategoryRemoteDataSource
) : ProductRepository {

    private val _products = MutableStateFlow<List<Product>>(emptyList())
    override val products: StateFlow<List<Product>> = _products.asStateFlow()

    private val _categories = MutableStateFlow<List<ProductCategory>>(emptyList())
    override val categories: StateFlow<List<ProductCategory>> = _categories.asStateFlow()

    private val _favoriteProductIds = MutableStateFlow<Set<Int>>(emptySet())
    override val favoriteProductIds: StateFlow<Set<Int>> = _favoriteProductIds.asStateFlow()

    init {
        Log.d("ProductRepo", "Initializing ProductRepositoryImpl")
        refreshProducts()
    }

    private fun refreshProducts() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                Log.d("ProductRepo", "Fetching categories...")
                val remoteCategories = storeCategoryDataSource.getCategories()
                Log.d("ProductRepo", "Fetched ${remoteCategories.size} categories")
                _categories.value = remoteCategories.map { ProductCategory(it.id, it.name) }

                Log.d("ProductRepo", "Fetching products...")
                val remoteProducts = storeProductDataSource.getProducts()
                Log.d("ProductRepo", "Fetched ${remoteProducts.size} products")

                _products.value = remoteProducts.map { dto ->
                    Product(
                        id = dto.id,
                        name = dto.title,
                        description = dto.description,
                        price = dto.price,
                        category = ProductCategory(dto.category.id, dto.category.name),
                        imageUrl = dto.images.firstOrNull() ?: ""
                    )
                }
                Log.d("ProductRepo", "Products updated: ${_products.value.size}")
            } catch (e: Exception) {
                Log.e("ProductRepo", "Error refreshing products", e)
            }
        }
    }

    override fun toggleFavorite(productId: Int) {
        val current = _favoriteProductIds.value.toMutableSet()
        if (current.contains(productId)) current.remove(productId) else current.add(productId)
        _favoriteProductIds.value = current
    }

    override suspend fun addCategory(name: String, description: String?) {
        storeCategoryDataSource.insertCategory(
            StoreCategoryInsertDto(name = name, image = "https://placehold.co/600x400")
        )
        refreshProducts()
    }

    override suspend fun updateCategory(categoryId: Int, name: String, description: String?) {
        storeCategoryDataSource.updateCategory(categoryId, StoreCategoryUpdateDto(name = name))
        refreshProducts()
    }

    override suspend fun deleteCategory(categoryId: Int) {
        storeCategoryDataSource.deleteCategory(categoryId)
        refreshProducts()
    }

    override suspend fun addProduct(
        name: String,
        description: String,
        price: Double,
        categoryId: Int,
        imageUrl: String
    ) {
        storeProductDataSource.insertProduct(
            StoreProductInsertDto(
                title = name,
                price = price,
                description = description,
                categoryId = categoryId,
                images = listOf(imageUrl)
            )
        )
        refreshProducts()
    }

    override suspend fun updateProduct(
        productId: Int,
        name: String,
        description: String,
        price: Double,
        categoryId: Int,
        imageUrl: String
    ) {
        storeProductDataSource.updateProduct(
            productId,
            StoreProductInsertDto(
                title = name,
                price = price,
                description = description,
                categoryId = categoryId,
                images = listOf(imageUrl)
            )
        )
        refreshProducts()
    }

    override suspend fun deleteProduct(productId: Int) {
        storeProductDataSource.deleteProduct(productId)
        refreshProducts()
    }
}
