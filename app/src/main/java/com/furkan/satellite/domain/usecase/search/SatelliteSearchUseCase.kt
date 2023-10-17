package com.furkan.satellite.domain.usecase.search

import com.furkan.satellite.data.model.Satellite
import com.furkan.satellite.domain.usecase.satellites.SatelliteListUseCase
import com.furkan.satellite.utils.Constant
import com.furkan.satellite.utils.Resource
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class SatelliteSearchUseCase @Inject constructor(
    private val satelliteListUseCase: SatelliteListUseCase,
){



    suspend operator fun invoke(request: SatelliteSearchParams): Flow<Resource<List<Satellite?>?>> {
        return flow {

            emit(Resource.Loading())
            delay(Constant.SEARCH_DELAY)

            satelliteListUseCase.invoke(SatelliteListUseCase.SatelliteListParams(request.fileName)).collect{response->
                    when (response) {
                        is Resource.Success -> Resource.Success(
                            emit(
                                Resource.Success(
                                    response.data?.filter {
                                        it?.name?.lowercase()?.contains(request.searchKey.lowercase()) == true
                                    }
                                )
                            )
                        )
                        is Resource.Failure -> Resource.Failure(response.error)
                        is Resource.Loading -> Resource.Loading()
                    }
            }
        }
    }
    data class SatelliteSearchParams(val fileName: String, val searchKey: String)
}