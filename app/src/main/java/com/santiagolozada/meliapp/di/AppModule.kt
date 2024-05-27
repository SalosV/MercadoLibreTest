package com.santiagolozada.meliapp.di

import com.santiagolozada.domain.usecase.ProductUseCase
import com.santiagolozada.domain.usecase.ProductUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface AppModule {

    @Binds
    fun bindSearchUseCase(searchUseCase: ProductUseCaseImpl): ProductUseCase
}