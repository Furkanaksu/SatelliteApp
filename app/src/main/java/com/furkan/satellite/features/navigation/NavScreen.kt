package com.furkan.satellite.features.navigation

sealed class NavScreen(val route: String) {
    object HomeScreen : NavScreen("home")



    object DetailScreen : NavScreen("detail"){
        const val id: String = "id"
        const val name: String = "name"
        val routeWithArgument: String = route.plus("/{$id}/{$name}")
    }

}