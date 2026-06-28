package com.aero.techstore.data.remote.api

import com.aero.techstore.data.remote.dto.StoreCategoryDto
import com.aero.techstore.data.remote.dto.StoreCategoryInsertDto
import com.aero.techstore.data.remote.dto.StoreCategoryUpdateDto
import com.aero.techstore.data.remote.dto.StoreProductDto
import com.aero.techstore.data.remote.dto.StoreProductInsertDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface StoreProductApiService {
    @GET("products/")
    suspend fun getProducts(): List<StoreProductDto>

    @POST("products/")
    suspend fun createProduct(@Body product: StoreProductInsertDto): StoreProductDto

    @PUT("products/{id}")
    suspend fun updateProduct(
        @Path("id") id: Int,
        @Body product: StoreProductInsertDto
    ): StoreProductDto

    @DELETE("products/{id}")
    suspend fun deleteProduct(@Path("id") id: Int): Boolean
}

interface StoreCategoryApiService {
    @GET("categories/")
    suspend fun getCategories(): List<StoreCategoryDto>

    @POST("categories/")
    suspend fun createCategory(@Body category: StoreCategoryInsertDto): StoreCategoryDto

    @PUT("categories/{id}")
    suspend fun updateCategory(
        @Path("id") id: Int,
        @Body category: StoreCategoryUpdateDto
    ): StoreCategoryDto

    @DELETE("categories/{id}")
    suspend fun deleteCategory(@Path("id") id: Int): Boolean
}
