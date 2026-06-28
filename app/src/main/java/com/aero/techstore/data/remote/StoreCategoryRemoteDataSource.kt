package com.aero.techstore.data.remote

import com.aero.techstore.data.remote.api.StoreCategoryApiService
import com.aero.techstore.data.remote.dto.StoreCategoryDto
import com.aero.techstore.data.remote.dto.StoreCategoryInsertDto
import com.aero.techstore.data.remote.dto.StoreCategoryUpdateDto
import javax.inject.Inject

interface StoreCategoryRemoteDataSource {
    suspend fun getCategories(): List<StoreCategoryDto>
    suspend fun insertCategory(category: StoreCategoryInsertDto)
    suspend fun updateCategory(categoryId: Int, category: StoreCategoryUpdateDto)
    suspend fun deleteCategory(categoryId: Int)
}

class StoreCategoryRemoteDataSourceImpl @Inject constructor(
    private val apiService: StoreCategoryApiService
) : StoreCategoryRemoteDataSource {

    override suspend fun getCategories(): List<StoreCategoryDto> =
        apiService.getCategories()

    override suspend fun insertCategory(category: StoreCategoryInsertDto) {
        apiService.createCategory(category)
    }

    override suspend fun updateCategory(categoryId: Int, category: StoreCategoryUpdateDto) {
        apiService.updateCategory(categoryId, category)
    }

    override suspend fun deleteCategory(categoryId: Int) {
        apiService.deleteCategory(categoryId)
    }
}
