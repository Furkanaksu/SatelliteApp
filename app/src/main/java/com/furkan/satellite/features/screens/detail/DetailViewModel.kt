package com.furkan.satellite.features.screens.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.furkan.satellite.data.local.entity.SatelliteDetail
import com.furkan.satellite.data.local.entity.SatellitePosition
import com.furkan.satellite.data.model.PositionCoordinate
import com.furkan.satellite.data.model.Satellite
import com.furkan.satellite.domain.usecase.detail.GetPositionUseCase
import com.furkan.satellite.domain.usecase.detail.GetSatelliteDetailUseCase
import com.furkan.satellite.features.base.BaseViewModel
import com.furkan.satellite.features.base.IViewEvent
import com.furkan.satellite.features.base.IViewState
import com.furkan.satellite.features.navigation.NavScreen
import com.furkan.satellite.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class DetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val satelliteDetail: GetSatelliteDetailUseCase,
    private val satellitePosition: GetPositionUseCase
) : BaseViewModel<DetailViewState, DetailViewEvent>() {

    init {
        savedStateHandle.get<String>(NavScreen.DetailScreen.id)?.let {
            setState { currentState.copy(id = it) }
        }
        savedStateHandle.get<String>(NavScreen.DetailScreen.name)?.let {
            setState { currentState.copy(shipName = it) }
        }
        getSatelliteDetail()
    }


    override fun createInitialState(): DetailViewState = DetailViewState()

    override fun onTriggerEvent(event: DetailViewEvent) {}


    private fun getSatelliteDetail() {
        viewModelScope.launch {
            satelliteDetail.execute(GetSatelliteDetailUseCase.Request(currentState.id.toInt())).collect { state->
                when (state) {
                    is Resource.Success -> {
                        satellitePosition()
                        setState {
                            currentState.copy(
                                data = state.data,
                                isLoading = false
                            )
                        }
                    }
                    is Resource.Failure -> {
                        setState {
                            currentState.copy(
                                error = state.error,
                                isLoading = false
                            )
                        }
                    }
                    is Resource.Loading -> {
                        setState {
                            currentState.copy(
                                isLoading = true
                            )
                        }
                    }

                    else -> {}
                }
            }

        }
    }

    private fun satellitePosition(){
        viewModelScope.launch {
            satellitePosition.execute(GetPositionUseCase.Request(currentState.id.toInt())).collect { state->
                when (state) {
                    is Resource.Success -> {
                        setState {
                            currentState.copy(
                                coordinate = state.data,
                                isLoading = false
                            )
                        }
                    }
                    is Resource.Failure -> {
                        setState {
                            currentState.copy(
                                error = state.error,
                                isLoading = false
                            )
                        }
                    }
                    is Resource.Loading -> {
                        setState {
                            currentState.copy(
                                isLoading = true
                            )
                        }
                    }

                    else -> {}
                }
            }
        }
    }

}

data class DetailViewState(
    var data : SatelliteDetail? = null,
    var coordinate: PositionCoordinate? = null,
    var isLoading : Boolean = false,
    var query : String = "",
    var error : String? = null,
    var id : String = "",
    var shipName : String = ""
) : IViewState


sealed interface DetailViewEvent : IViewEvent {
    object Loading : DetailViewEvent
    class Success(val list: List<Satellite?>?) : DetailViewEvent
    class Failure(val errorName: String) : DetailViewEvent
}


