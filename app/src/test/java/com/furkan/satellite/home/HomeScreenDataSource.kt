package com.furkan.satellite.home

import com.furkan.satellite.data.model.Satellite

object HomeScreenDataSource {

    fun successData() : List<Satellite> {
        return listOf(
            Satellite(
                1,
                true,
                "test",
            )
        )
    }

}