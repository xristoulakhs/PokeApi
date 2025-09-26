package com.example.pokemonexplorerapp.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.pokemonexplorerapp.ui.screens.HomeScreen
import com.example.pokemonexplorerapp.ui.screens.ResultsScreen
import com.example.pokemonexplorerapp.ui.screens.WelcomeScreen

@Composable
fun AppNavigation(navController: NavHostController) {

    var startDestination = "welcome"

    NavHost(navController = navController, startDestination = startDestination) {
        //welcome screen
        composable("welcome") {
            WelcomeScreen(navController = navController)
        }

        //welcome screen
        composable("home") {
            HomeScreen(navController = navController)
        }

        //results
        composable("results"){
            ResultsScreen(navController = navController)
        }
    }
}