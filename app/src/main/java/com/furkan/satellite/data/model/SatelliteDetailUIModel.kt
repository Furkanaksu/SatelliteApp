package com.furkan.satellite.data.model

import com.furkan.satellite.data.local.entity.SatellitePosition


data class SatelliteDetailUIModel(
    val satelliteId: Int?,
    val heightMassText: String?,
    val costText: String?,
    val dateText: String?,
    val lastPosition: List<SatellitePosition?>?
)
