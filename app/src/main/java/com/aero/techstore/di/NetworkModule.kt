package com.aero.techstore.di

import com.aero.techstore.data.remote.api.StoreCategoryApiService
import com.aero.techstore.data.remote.api.StoreProductApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.auth.MemorySessionManager
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideSupabaseClient(): SupabaseClient {
        return createSupabaseClient(
            supabaseUrl = "https://dukiljeypspkmjbqmeah.supabase.co",
            supabaseKey = "sb_publishable_Py-S-bHCz4BaFTcIaW-IKg_opwYmh37"
        ) {
            install(Postgrest)
            install(Auth) {
                sessionManager = MemorySessionManager()
            }
        }
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            )
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.escuelajs.co/api/v1/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideStoreProductApiService(retrofit: Retrofit): StoreProductApiService =
        retrofit.create(StoreProductApiService::class.java)

    @Provides
    @Singleton
    fun provideStoreCategoryApiService(retrofit: Retrofit): StoreCategoryApiService =
        retrofit.create(StoreCategoryApiService::class.java)
}
