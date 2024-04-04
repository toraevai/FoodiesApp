package com.example.foodies.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.example.foodies.ui.catalogScreen.CatalogScreen
import com.example.foodies.ui.catalogScreen.CatalogScreenDestination
import com.example.foodies.ui.catalogScreen.CatalogScreenViewModel

@Composable
fun FoodiesAppNavHost() {
    val navController = rememberNavController()
    val foodiesAppViewModel = hiltViewModel<CatalogScreenViewModel>()
    val catalogScreenUiState = foodiesAppViewModel.catalogScreenUiState
    NavHost(
        navController = navController,
        startDestination = CatalogScreenDestination.route
    )
    {
        composable(route = CatalogScreenDestination.route) {
            CatalogScreen(catalogScreenUiState = catalogScreenUiState)
        }
    }
}