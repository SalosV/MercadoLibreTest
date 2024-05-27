package com.santiagolozada.data.di

import com.santiagolozada.data.repository.ProductRepositoryImpl
import com.santiagolozada.domain.repository.ProductRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds
    fun bindSearchRepository(searchRepository: ProductRepositoryImpl): ProductRepository

}