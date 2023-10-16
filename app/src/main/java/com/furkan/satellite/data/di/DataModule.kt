package com.furkan.satellite.data.di

import android.content.Context
import androidx.room.Room
import com.furkan.satellite.data.local.dao.SatelliteDao
import com.furkan.satellite.data.local.database.Converters
import com.furkan.satellite.data.local.database.SatelliteDatabase
import com.furkan.satellite.data.local.source.file.FileDataSource
import com.furkan.satellite.data.local.source.file.FileDataSourceImpl
import com.furkan.satellite.data.local.source.local.LocalDataSource
import com.furkan.satellite.data.local.source.local.LocalDataSourceImpl
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    @Singleton
    fun provideFileDataSource(
        gson: Gson,
        @ApplicationContext appContext: Context,
    ): FileDataSource = FileDataSourceImpl(gson, appContext)

    @Provides
    @Singleton
    fun provideRoomDataSource(dao: SatelliteDao): LocalDataSource = LocalDataSourceImpl(dao)

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext appContext: Context,
        typeConverters: Converters
    ): SatelliteDatabase {
        return Room
            .databaseBuilder(appContext, SatelliteDatabase::class.java, "satellite.db")
            .addTypeConverter(typeConverters)
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
    }

    @Provides
    @Singleton
    fun provideSatelliteDao(db: SatelliteDatabase): SatelliteDao {
        return db.getSatelliteDao()
    }
}