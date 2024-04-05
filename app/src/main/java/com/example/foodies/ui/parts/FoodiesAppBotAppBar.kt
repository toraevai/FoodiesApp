package com.example.foodies.ui.parts

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.example.foodies.R
import com.example.foodies.model.Product
import com.example.foodies.navigation.NavigationDestination
import com.example.foodies.ui.CartScreenDestination
import com.example.foodies.ui.CatalogScreenDestination
import com.example.foodies.ui.ProductCardDestination
import java.text.NumberFormat

@Composable
fun FoodiesAppBotAppBar(
    onButtonClick: () -> Unit,
    cart: Map<Product, Int>,
    currentDestination: NavigationDestination,
    modifier: Modifier = Modifier,
    currentProduct: Product
) {
    BottomAppBar {
        Button(
            onClick = onButtonClick,
            modifier = modifier
                .fillMaxSize()
                .padding(
                    horizontal = dimensionResource(id = R.dimen.padding_large),
                    vertical = dimensionResource(id = R.dimen.padding_medium)
                ),
            shape = MaterialTheme.shapes.medium
        ) {
            when(currentDestination) {
                is CatalogScreenDestination -> Icon(
                    imageVector = Icons.Default.ShoppingCart,
                    contentDescription = stringResource(R.string.buy)
                )
                is ProductCardDestination -> Text(
                    text = stringResource(R.string.add_to_cart_for),
                    style = MaterialTheme.typography.titleSmall
                )
                is CartScreenDestination -> Text(
                    text = stringResource(R.string.order_for),
                    style = MaterialTheme.typography.titleSmall
                )
            }
            Spacer(modifier = modifier.width(dimensionResource(id = R.dimen.padding_small)))
            when(currentDestination) {
                is ProductCardDestination -> Text(
                    text = "${currentProduct.priceCurrent / 100} ${NumberFormat.getCurrencyInstance().currency!!.symbol}",
                    style = MaterialTheme.typography.titleSmall
                )
                else -> Text(
                    text = "${cart.filter { it.value != 0 }.map { it.key.priceCurrent * it.value }
                        .sumOf { it } / 100} ${NumberFormat.getCurrencyInstance().currency!!.symbol}",
                    style = MaterialTheme.typography.titleSmall
                )
            }
        }
    }
}