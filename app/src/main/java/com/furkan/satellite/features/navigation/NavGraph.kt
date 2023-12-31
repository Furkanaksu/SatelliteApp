package com.furkan.satellite.features.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.furkan.satellite.features.screens.detail.DetailScreen
import com.furkan.satellite.features.screens.detail.DetailViewModel
import com.furkan.satellite.features.screens.main.HomeScreen
import com.furkan.satellite.features.screens.main.HomeViewModel

@Composable
fun NavGraph(
    startDestination: String = NavScreen.HomeScreen.route
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(NavScreen.HomeScreen.route) {
            val viewModel: HomeViewModel = hiltViewModel()
            HomeScreen(
                viewModel = viewModel,
                onClickItem = { id, name ->
                    navController.navigate(NavScreen.DetailScreen.route.plus("/${id}/${name}"))
                }
            )
        }

        composable(NavScreen.DetailScreen.routeWithArgument) {
            val viewModel: DetailViewModel = hiltViewModel()
            DetailScreen(
                viewModel = viewModel,
                onBackPressed = {
                    navController.popBackStack()
                }
            )
        }
    }
}