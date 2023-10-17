package com.furkan.satellite.features.screens.navigation

sealed class NavScreen(val route: String) {
    object HomeScreen : NavScreen("home")
}