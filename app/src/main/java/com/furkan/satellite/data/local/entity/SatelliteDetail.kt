package com.furkan.satellite.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "satellite_detail")
data class SatelliteDetail(
    @PrimaryKey(autoGenerate = true)
    val id : Int,
    @SerializedName("cost_per_launch")
    val costPerLaunch: Long,
    @SerializedName("first_flight")
    val firstFlight: String,
    val height: Int,
    val mass: Long
)
