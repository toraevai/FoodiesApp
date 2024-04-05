package com.example.foodies.ui

import androidx.compose.runtime.Composable
import com.example.foodies.navigation.NavigationDestination

object CartScreenDestination : NavigationDestination {
    override val route = "cart_screen"
    override val title = null
}
@Composable
fun CartScreen() {
}