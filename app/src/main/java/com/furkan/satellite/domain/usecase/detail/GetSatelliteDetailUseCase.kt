package com.furkan.satellite.domain.usecase.detail

import com.furkan.satellite.data.local.entity.SatelliteDetail
import com.furkan.satellite.domain.repository.SatelliteRepository
import com.furkan.satellite.utils.Constant.SATELLITE_DETAIL_FILE
import com.furkan.satellite.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class GetSatelliteDetailUseCase @Inject constructor(
    private val repository: SatelliteRepository,
    private val insertSatelliteDetailUseCase: InsertSatelliteDetailUseCase
) {

    suspend fun execute(request: Request): Flow<Resource<SatelliteDetail?>?> {
        return flow {
            emit(Resource.Loading())
            when (val satelliteDetailList =
                repository.getSatelliteDetail(SATELLITE_DETAIL_FILE, request.satelliteId)) {
                is Resource.Success -> {
                    insertSatelliteDetailUseCase.execute(
                        InsertSatelliteDetailUseCase.Request(
                            satelliteDetailList.data?.find { it?.id == request.satelliteId })
                    )
                    emit(Resource.Success(satelliteDetailList.data?.first { it?.id == request.satelliteId }))
                }
                is Resource.Failure -> emit(Resource.Failure(satelliteDetailList.error))
                is Resource.Loading -> emit(Resource.Loading())
            }
        }
    }

    data class Request(val satelliteId: Int)

}