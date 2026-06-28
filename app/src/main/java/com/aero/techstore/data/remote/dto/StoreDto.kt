package com.aero.techstore.data.remote.dto

data class StoreProductDto(
    val id: Int,
    val title: String,
    val price: Double,
    val description: String,
    val category: StoreCategoryDto,
    val images: List<String>
)

data class StoreProductInsertDto(
    val title: String,
    val price: Double,
    val description: String,
    val categoryId: Int,
    val images: List<String>
)

data class StoreCategoryDto(
    val id: Int,
    val name: String,
    val image: String
)

data class StoreCategoryInsertDto(
    val name: String,
    val image: String
)

data class StoreCategoryUpdateDto(
    val name: String
)
