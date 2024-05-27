package com.santiagolozada.meliapp.di

import com.santiagolozada.domain.repository.ProductRepository
import com.santiagolozada.domain.usecase.ProductUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {

    @Provides
    @Singleton
    fun provideSearchUseCase(productRepository: ProductRepository): ProductUseCaseImpl {
        return ProductUseCaseImpl(productRepository)
    }

}