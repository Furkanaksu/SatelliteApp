package com.furkan.satellite.data.local.source.local

import com.furkan.satellite.data.local.entity.SatelliteDetail
import com.furkan.satellite.data.local.entity.SatellitePosition
import com.furkan.satellite.utils.Resource

interface LocalDataSource {
    suspend fun getSatelliteDetails(): Resource<List<SatelliteDetail>?>
    suspend fun getSatellitePositions(): Resource<List<SatellitePosition>?>
    suspend fun insertSatelliteDetail(satelliteDetail: SatelliteDetail)
    suspend fun insertSatellitePosition(satellitePosition: SatellitePosition)
    suspend fun deleteSatelliteDetail(satelliteDetail: SatelliteDetail)
    suspend fun deleteSatellitePosition(satellitePosition: SatellitePosition)
}