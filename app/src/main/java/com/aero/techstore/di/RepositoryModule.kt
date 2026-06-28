package com.aero.techstore.di

import com.aero.techstore.data.remote.CategoryRemoteDataSource
import com.aero.techstore.data.remote.CategoryRemoteDataSourceImpl
import com.aero.techstore.data.remote.StoreCategoryRemoteDataSource
import com.aero.techstore.data.remote.StoreCategoryRemoteDataSourceImpl
import com.aero.techstore.data.remote.StoreProductRemoteDataSource
import com.aero.techstore.data.remote.StoreProductRemoteDataSourceImpl
import com.aero.techstore.data.remote.ProductRemoteDataSource
import com.aero.techstore.data.remote.ProductRemoteDataSourceImpl
import com.aero.techstore.data.repository.CartRepositoryImpl
import com.aero.techstore.data.repository.ProductRepositoryImpl
import com.aero.techstore.data.repository.ThemeRepositoryImpl
import com.aero.techstore.domain.repository.CartRepository
import com.aero.techstore.domain.repository.ProductRepository
import com.aero.techstore.domain.repository.ThemeRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindProductRepository(
        productRepositoryImpl: ProductRepositoryImpl
    ): ProductRepository

    @Binds
    @Singleton
    abstract fun bindCartRepository(
        cartRepositoryImpl: CartRepositoryImpl
    ): CartRepository

    @Binds
    @Singleton
    abstract fun bindThemeRepository(
        themeRepositoryImpl: ThemeRepositoryImpl
    ): ThemeRepository

    @Binds
    @Singleton
    abstract fun bindProductRemoteDataSource(
        productRemoteDataSourceImpl: ProductRemoteDataSourceImpl
    ): ProductRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindCategoryRemoteDataSource(
        categoryRemoteDataSourceImpl: CategoryRemoteDataSourceImpl
    ): CategoryRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindStoreProductRemoteDataSource(
        impl: StoreProductRemoteDataSourceImpl
    ): StoreProductRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindStoreCategoryRemoteDataSource(
        impl: StoreCategoryRemoteDataSourceImpl
    ): StoreCategoryRemoteDataSource
}
