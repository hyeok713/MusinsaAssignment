package com.hyeokbeom.data.di

import com.hyeokbeom.data.repository.MusinsaRepositoryImpl
import com.hyeokbeom.domain.repository.MusinsaRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class DataModule {
    @Binds
    abstract fun musinsaRepositoryBinder(
        repository: MusinsaRepositoryImpl,
    ): MusinsaRepository
}