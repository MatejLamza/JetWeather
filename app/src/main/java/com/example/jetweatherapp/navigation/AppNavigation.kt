package com.example.jetweatherapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.jetweatherapp.ui.home.HomeRoute
import com.example.jetweatherapp.ui.home.HomeViewModel
import com.example.jetweatherapp.ui.settings.SettingsScreen


internal sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Settings : Screen("search")
    object Favourites : Screen("favourites")
}

@Composable
internal fun AppNavigation(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    homeViewModel: HomeViewModel
) {
    NavHost(navController = navController, startDestination = Screen.Home.route) {
        composable(Screen.Home.route) {
            HomeRoute(homeViewModel = homeViewModel)
        }

        composable(
            Screen.Settings.route + "/{movie}",
            arguments = listOf(navArgument(name = "movie") { type = NavType.StringType })
        ) {
            SettingsScreen(navController, it.arguments?.getString("movie")!!)
        }
    }
}


