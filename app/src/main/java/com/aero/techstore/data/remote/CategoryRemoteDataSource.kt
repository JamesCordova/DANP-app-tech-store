package com.aero.techstore.data.remote

import com.aero.techstore.data.remote.dto.CategoryDto
import com.aero.techstore.data.remote.dto.CategoryInsertDto
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.postgrest
import javax.inject.Inject

interface CategoryRemoteDataSource {
    suspend fun getCategories(): List<CategoryDto>
    suspend fun insertCategory(category: CategoryInsertDto)
    suspend fun updateCategory(categoryId: Int, category: CategoryInsertDto)
    suspend fun deleteCategory(categoryId: Int)
}

class CategoryRemoteDataSourceImpl @Inject constructor(
    private val supabaseClient: SupabaseClient
) : CategoryRemoteDataSource {

    override suspend fun getCategories(): List<CategoryDto> {
        return supabaseClient.postgrest["ProductCategories"].select().decodeList<CategoryDto>()
    }

    override suspend fun insertCategory(category: CategoryInsertDto) {
        supabaseClient.postgrest["ProductCategories"].insert(category)
    }

    override suspend fun updateCategory(categoryId: Int, category: CategoryInsertDto) {
        supabaseClient.postgrest["ProductCategories"].update(category) {
            filter {
                eq("id", categoryId)
            }
        }
    }

    override suspend fun deleteCategory(categoryId: Int) {
        supabaseClient.postgrest["ProductCategories"].delete {
            filter {
                eq("id", categoryId)
            }
        }
    }
}
