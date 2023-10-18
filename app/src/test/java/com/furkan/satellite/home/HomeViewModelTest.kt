package com.furkan.satellite.home

import com.furkan.satellite.data.model.Satellite
import com.furkan.satellite.domain.usecase.satellites.SatelliteListUseCase
import com.furkan.satellite.domain.usecase.search.SatelliteSearchUseCase
import com.furkan.satellite.features.screens.main.HomeViewModel
import com.furkan.satellite.utils.Constant
import com.furkan.satellite.utils.Resource
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)

class HomeViewModelTest {


    private lateinit var viewModel: HomeViewModel

    @Mock
    private lateinit var satelliteListUseCase : SatelliteListUseCase

    @Mock
    private lateinit var satelliteSearchUseCase : SatelliteSearchUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(Dispatchers.Unconfined)

        viewModel = HomeViewModel(
            satelliteListUseCase,
            satelliteSearchUseCase
        )
    }


    @Test
    fun testGetSatelliteList() = runBlocking {
        val satelliteList = listOf(
            Satellite(
                1,
                true,
                "1",
            )
        )

        whenever(satelliteListUseCase.invoke(SatelliteListUseCase.SatelliteListParams(Constant.SATELLITE_FILE))).thenReturn(
            object : Flow<Resource<List<Satellite?>?>> {
                override suspend fun collect(collector: kotlinx.coroutines.flow.FlowCollector<Resource<List<Satellite?>?>>) {
                    collector.emit(Resource.Success(satelliteList))
                }
            }
        )

        viewModel.getSatelliteList()
        verify(satelliteListUseCase, times(1)).invoke(SatelliteListUseCase.SatelliteListParams(Constant.SATELLITE_FILE))

        assertEquals(viewModel.currentState.data, satelliteList)
    }



    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}

