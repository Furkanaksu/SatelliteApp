package com.furkan.satellite.domain.usecase.detail

import com.furkan.satellite.data.local.entity.SatellitePosition
import com.furkan.satellite.domain.repository.SatelliteRepository
import com.furkan.satellite.domain.usecase.BaseUseCase
import com.furkan.satellite.utils.Resource

import javax.inject.Inject

class InsertSatellitePositionUseCase @Inject constructor(
    private val repository: SatelliteRepository
) : BaseUseCase<InsertSatellitePositionUseCase.Request, Unit>() {
    override suspend fun execute(request: Request): Resource<Unit> {
        return try {
            request.satellitePosition?.let { repository.insertSatellitePosition(it) }
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Failure(e.toString())
        }
    }

    data class Request(val satellitePosition: SatellitePosition?)
}