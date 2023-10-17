package com.furkan.satellite.domain.usecase.satellites

import com.furkan.satellite.data.model.Satellite
import com.furkan.satellite.domain.repository.SatelliteRepository
import com.furkan.satellite.utils.Resource

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SatelliteListUseCase @Inject constructor(
    private val repository: SatelliteRepository
){
    suspend operator fun invoke(request: SatelliteListParams): Flow<Resource<List<Satellite?>?>> {
        return flow {
            emit(Resource.Loading())
            val response = repository.getSatellites(request.fileName)
            try {
                when (response) {
                    is Resource.Success -> Resource.Success(
                        emit(
                            Resource.Success(response.data)
                        )
                    )
                    is Resource.Failure -> Resource.Failure(response.error)
                    is Resource.Loading -> Resource.Loading()
                }
            } catch (e: Exception) {
                emit(Resource.Failure("Error"))
            }
        }
    }
    data class SatelliteListParams(val fileName: String)
}
