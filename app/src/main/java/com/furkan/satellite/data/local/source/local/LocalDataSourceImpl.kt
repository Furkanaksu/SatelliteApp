package com.furkan.satellite.data.local.source.local

import com.furkan.satellite.data.local.dao.SatelliteDao
import com.furkan.satellite.data.local.entity.SatelliteDetail
import com.furkan.satellite.data.local.entity.SatellitePosition
import com.furkan.satellite.utils.Constant.LIST_EMPTY_ERROR
import com.furkan.satellite.utils.Resource

import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(private val dao: SatelliteDao) : LocalDataSource {
    override suspend fun getSatelliteDetails(): Resource<List<SatelliteDetail>?> {
        val satelliteDetails = dao.getSatelliteDetails()
        return if (satelliteDetails?.isNotEmpty() == true) {
            Resource.Success(satelliteDetails)
        } else Resource.Failure(LIST_EMPTY_ERROR)
    }

    override suspend fun getSatellitePositions(): Resource<List<SatellitePosition>?> {
        val satellitePositions = dao.getSatellitePositions()
        return if (satellitePositions?.isNotEmpty() == true) {
            Resource.Success(satellitePositions)
        } else Resource.Failure(LIST_EMPTY_ERROR)
    }

    override suspend fun insertSatelliteDetail(satelliteDetail: SatelliteDetail) {
        dao.insertSatelliteDetail(satelliteDetail)
    }

    override suspend fun insertSatellitePosition(satellitePosition: SatellitePosition) {
        dao.deleteSatellitePosition(satellitePosition)
        dao.insertSatellitePosition(satellitePosition)
    }

    override suspend fun deleteSatelliteDetail(satelliteDetail: SatelliteDetail) {
        dao.deleteSatelliteDetail(satelliteDetail)
    }

    override suspend fun deleteSatellitePosition(satellitePosition: SatellitePosition) {
        dao.deleteSatellitePosition(satellitePosition)
    }
}