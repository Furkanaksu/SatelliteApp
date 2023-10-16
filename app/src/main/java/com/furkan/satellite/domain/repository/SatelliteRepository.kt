package com.furkan.satellite.domain.repository

import com.furkan.satellite.data.local.entity.SatelliteDetail
import com.furkan.satellite.data.local.entity.SatellitePosition
import com.furkan.satellite.data.model.Satellite
import com.furkan.satellite.utils.Resource


interface SatelliteRepository {
    suspend fun getSatellites(fileName: String): Resource<List<Satellite>?>

    suspend fun getSatelliteDetail(
        fileName: String,
        satelliteId: Int
    ): Resource<List<SatelliteDetail?>?>

    suspend fun getSatellitePositions(
        fileName: String,
        satelliteId: Int
    ): Resource<List<SatellitePosition?>?>

    suspend fun insertSatelliteDetail(satelliteDetail: SatelliteDetail)

    suspend fun insertSatellitePosition(satellitePosition: SatellitePosition)
}