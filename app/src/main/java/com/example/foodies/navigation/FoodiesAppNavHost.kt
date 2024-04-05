package com.example.foodies.navigation

import android.provider.ContactsContract.Profile
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.foodies.model.fakeProduct
import com.example.foodies.ui.CatalogScreen
import com.example.foodies.ui.CatalogScreenDestination
import com.example.foodies.ui.CatalogScreenUiState
import com.example.foodies.ui.FoodiesAppViewModel
import com.example.foodies.ui.ProductCardDestination
import com.example.foodies.ui.ProductCardScreen

@Composable
fun FoodiesAppNavHost() {
    val navController = rememberNavController()
    val foodiesAppViewModel = hiltViewModel<FoodiesAppViewModel>()
    val catalogScreenUiState = foodiesAppViewModel.catalogScreenUiState
    NavHost(
        navController = navController,
        startDestination = CatalogScreenDestination.route
    )
    {
        composable(
            route = CatalogScreenDestination.route
        ) {
            CatalogScreen(
                catalogScreenUiState = catalogScreenUiState,
                goToCartScreen = {},
                onCardClick = { product ->
                    navController.navigate(ProductCardDestination.route + "/${product.id}")
                }
            )
        }
        composable(
            route = ProductCardDestination.route + "/{productId}",
            arguments = listOf(navArgument("productId") { type = NavType.StringType })
        ) { backStackEntry ->
            val productId = backStackEntry.arguments?.getString("productId")!!.toInt()
            val product =
                if (foodiesAppViewModel.catalogScreenUiState is CatalogScreenUiState.Success) (foodiesAppViewModel.catalogScreenUiState as CatalogScreenUiState.Success).productsList.find { it.id == productId } else fakeProduct
            ProductCardScreen(
                catalogScreenUiState = catalogScreenUiState,
                product = product!!,
                navigateBack = { navController.navigateUp() }
            )
        }
    }
}