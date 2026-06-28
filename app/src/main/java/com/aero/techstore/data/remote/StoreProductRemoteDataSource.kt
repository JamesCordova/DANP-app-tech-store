package com.aero.techstore.data.remote

import com.aero.techstore.data.remote.api.StoreProductApiService
import com.aero.techstore.data.remote.dto.StoreProductDto
import com.aero.techstore.data.remote.dto.StoreProductInsertDto
import javax.inject.Inject

interface StoreProductRemoteDataSource {
    suspend fun getProducts(): List<StoreProductDto>
    suspend fun insertProduct(product: StoreProductInsertDto)
    suspend fun updateProduct(productId: Int, product: StoreProductInsertDto)
    suspend fun deleteProduct(productId: Int)
}

class StoreProductRemoteDataSourceImpl @Inject constructor(
    private val apiService: StoreProductApiService
) : StoreProductRemoteDataSource {

    override suspend fun getProducts(): List<StoreProductDto> =
        apiService.getProducts()

    override suspend fun insertProduct(product: StoreProductInsertDto) {
        apiService.createProduct(product)
    }

    override suspend fun updateProduct(productId: Int, product: StoreProductInsertDto) {
        apiService.updateProduct(productId, product)
    }

    override suspend fun deleteProduct(productId: Int) {
        apiService.deleteProduct(productId)
    }
}
