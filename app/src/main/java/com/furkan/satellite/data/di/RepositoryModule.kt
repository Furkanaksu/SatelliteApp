package com.furkan.satellite.data.di

import com.furkan.satellite.data.repository.SatelliteRepositoryImpl
import com.furkan.satellite.domain.repository.SatelliteRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindSatelliteRepository(repository: SatelliteRepositoryImpl): SatelliteRepository
}