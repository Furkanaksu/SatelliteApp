package com.furkan.satellite.data.local.source.file

import com.furkan.satellite.data.local.entity.SatelliteDetail
import com.furkan.satellite.data.model.Satellite
import com.furkan.satellite.data.model.SatellitePositionsList
import com.furkan.satellite.utils.Resource

interface FileDataSource {
    suspend fun getSatelliteList(fileName: String): Resource<List<Satellite>?>
    suspend fun getSatelliteDetail(fileName: String): Resource<List<SatelliteDetail>?>
    suspend fun getSatellitePosition(fileName: String): Resource<SatellitePositionsList?>
}