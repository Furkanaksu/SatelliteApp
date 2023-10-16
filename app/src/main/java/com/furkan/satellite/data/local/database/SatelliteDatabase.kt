package com.furkan.satellite.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.furkan.satellite.data.local.dao.SatelliteDao
import com.furkan.satellite.data.local.entity.SatelliteDetail
import com.furkan.satellite.data.local.entity.SatellitePosition


@Database(
    entities = [SatelliteDetail::class, SatellitePosition::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class SatelliteDatabase : RoomDatabase() {
    abstract fun getSatelliteDao(): SatelliteDao
}