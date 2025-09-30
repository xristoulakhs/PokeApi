package com.example.pokemonexplorerapp.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.pokemonexplorerapp.ui.screens.home.HomeScreen
import com.example.pokemonexplorerapp.ui.screens.ResultsScreen
import com.example.pokemonexplorerapp.ui.screens.WelcomeScreen
import com.example.pokemonexplorerapp.ui.screens.home.HomeViewModel

@Composable
fun AppNavigation(navController: NavHostController) {

    val startDestination = "welcome"
    val homeViewModel: HomeViewModel = remember { HomeViewModel() }

    NavHost(navController = navController, startDestination = startDestination) {
        //welcome screen
        composable("welcome") {
            WelcomeScreen(navController = navController)
        }

        //welcome screen
        composable("home") {
            HomeScreen(navController = navController, homeViewModel = homeViewModel)
        }

        //results
        composable("results",
            ){
            ResultsScreen(navController = navController,homeViewModel)
        }
    }
}