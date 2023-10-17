package com.furkan.satellite.features.screens.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.furkan.satellite.data.model.Satellite
import com.furkan.satellite.domain.usecase.satellites.SatelliteListUseCase
import com.furkan.satellite.domain.usecase.search.SatelliteSearchUseCase
import com.furkan.satellite.features.base.BaseViewModel
import com.furkan.satellite.features.base.IViewEvent
import com.furkan.satellite.features.base.IViewState
import com.furkan.satellite.utils.Constant
import com.furkan.satellite.utils.Constant.SATELLITE_FILE
import com.furkan.satellite.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val satelliteListUseCase: SatelliteListUseCase,
    private val satelliteListSearchUseCase: SatelliteSearchUseCase
) : BaseViewModel<HomeViewState, HomeViewEvent>() {

    private lateinit var textChangeCountDownJob: Job


    init {
        getSatelliteList()
    }


    override fun createInitialState(): HomeViewState = HomeViewState()

    override fun onTriggerEvent(event: HomeViewEvent) {}


    fun setTextQuery(query: String) {
        viewModelScope.launch {
            setState {
                currentState.copy(
                    query = query
                )
            }

            if(::textChangeCountDownJob.isInitialized){
                textChangeCountDownJob.cancel()
            }

            textChangeCountDownJob = viewModelScope.launch {
                    delay(Constant.SEARCH_DELAY)
                    satelliteListSearchUseCase.invoke(SatelliteSearchUseCase.SatelliteSearchParams(SATELLITE_FILE, query)).collect{state->
                        when (state) {
                            is Resource.Success -> {
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
                        }
                    }
            }

        }
    }

    fun clearTextQuery() {
        viewModelScope.launch {
            setState {
                currentState.copy(
                    query = ""
                )
            }
            getSatelliteList()
        }
    }


    private fun getSatelliteList() {
        viewModelScope.launch {
            satelliteListUseCase.invoke(SatelliteListUseCase.SatelliteListParams(SATELLITE_FILE)).collect { state->
                when (state) {
                    is Resource.Success -> {
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
                }
            }

        }
    }



}

data class HomeViewState(
    var data : List<Satellite?>? = null,
    var isLoading : Boolean = false,
    var query : String = "",
    var error : String? = null
) : IViewState


sealed interface HomeViewEvent : IViewEvent {
    object Loading : HomeViewEvent
    class Success(val list: List<Satellite?>?) : HomeViewEvent
    class Failure(val errorName: String) : HomeViewEvent
}


