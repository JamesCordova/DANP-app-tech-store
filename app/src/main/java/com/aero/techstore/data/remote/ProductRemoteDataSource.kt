package com.aero.techstore.data.remote

import com.aero.techstore.data.remote.dto.ProductDto
import com.aero.techstore.data.remote.dto.ProductInsertDto
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.postgrest
import javax.inject.Inject

interface ProductRemoteDataSource {
    suspend fun getProducts(): List<ProductDto>
    suspend fun insertProduct(product: ProductInsertDto)
    suspend fun updateProduct(productId: Int, product: ProductInsertDto)
    suspend fun deleteProduct(productId: Int)
}

class ProductRemoteDataSourceImpl @Inject constructor(
    private val supabaseClient: SupabaseClient
) : ProductRemoteDataSource {

    override suspend fun getProducts(): List<ProductDto> {
        return supabaseClient.postgrest["Products"].select().decodeList<ProductDto>()
    }

    override suspend fun insertProduct(product: ProductInsertDto) {
        supabaseClient.postgrest["Products"].insert(product)
    }

    override suspend fun updateProduct(productId: Int, product: ProductInsertDto) {
        supabaseClient.postgrest["Products"].update(product) {
            filter {
                eq("id", productId)
            }
        }
    }

    override suspend fun deleteProduct(productId: Int) {
        supabaseClient.postgrest["Products"].delete {
            filter {
                eq("id", productId)
            }
        }
    }
}
