package com.furkan.satellite.domain.usecase.detail


import com.furkan.satellite.data.local.entity.SatellitePosition
import com.furkan.satellite.data.model.PositionCoordinate
import com.furkan.satellite.domain.repository.SatelliteRepository
import com.furkan.satellite.utils.Constant.SATELLITE_POSITION_FILE
import com.furkan.satellite.utils.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

class GetPositionUseCase @Inject constructor(
    private val repository: SatelliteRepository,
    private val insertSatellitePositionUseCase: InsertSatellitePositionUseCase
) {

    private var job: Job? = null


    suspend fun execute(request: Request): Flow<Resource<PositionCoordinate?>?> {
        return channelFlow {
            job?.cancel()
            val positions = repository.getSatellitePositions(SATELLITE_POSITION_FILE, request.satelliteId)
            when (positions) {
                is Resource.Success -> {
                    insertSatellitePositionUseCase.execute(
                        InsertSatellitePositionUseCase.Request(
                            positions.data?.find { it?.id == request.satelliteId })
                        )

                    val filterList = positions.data?.filter { it?.id == request.satelliteId }

                    send(Resource.Success( filterList?.firstOrNull()?.positions?.get(0)))
                    delay(3000)
                    send(Resource.Success( filterList?.firstOrNull()?.positions?.get(1)))
                    delay(3000)
                    send(Resource.Success( filterList?.firstOrNull()?.positions?.get(2)))
                }

                is Resource.Failure -> send(Resource.Failure(error = positions.error))
                is Resource.Loading -> send(Resource.Loading())
            }
        }
    }

    data class Request(val satelliteId: Int)
}