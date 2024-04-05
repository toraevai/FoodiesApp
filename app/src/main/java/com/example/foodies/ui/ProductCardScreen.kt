package com.example.foodies.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.foodies.R
import com.example.foodies.model.Product
import com.example.foodies.model.ProductTag
import com.example.foodies.navigation.NavigationDestination
import com.example.foodies.ui.parts.FoodiesBotAppBar
import com.example.foodies.ui.parts.ProductImageWithTag

object ProductCardDestination : NavigationDestination {
    override val route = "product_card"
}

@Composable
fun ProductCardScreen(
    catalogScreenUiState: CatalogScreenUiState,
    product: Product,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    when (catalogScreenUiState) {
        is CatalogScreenUiState.Success -> {
            SuccessProductCardScreen(
                product = product,
                listOfTags = catalogScreenUiState.listOfTags,
                navigateBack = navigateBack,
                cart = catalogScreenUiState.cart.value,
                addToCart = { catalogScreenUiState.increaseCounter(product) },
                modifier = modifier
            )
        }
        else -> {}
    }
}

@Composable
fun SuccessProductCardScreen(
    product: Product,
    listOfTags: List<ProductTag>,
    navigateBack: () -> Unit,
    cart: Map<Product, Int>,
    addToCart: (Product) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        bottomBar = {
            FoodiesBotAppBar(
                onButtonClick = { addToCart(product) },
                cart = cart,
                currentDestination = ProductCardDestination,
                currentProduct = product
            )
        }
    ) { paddingValues ->
        LazyColumn(modifier = modifier.padding(paddingValues)) {
            item {
                ProductImageWithTag(
                    product = product,
                    listOfTags = listOfTags,
                    currentDestination = ProductCardDestination,
                    onBackClick = navigateBack
                )
                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_medium) * 2))
                Text(
                    text = product.name,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.padding_large))
                )
                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_small)))
                Text(
                    text = product.description,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.padding_large))
                )
                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_medium) * 2))
                Divider(thickness = 1.dp)
                ProductInfo(
                    info = stringResource(R.string.weight),
                    value = product.measure.toDouble(),
                    measureUnit = product.measureUnit
                )
                Divider(thickness = 1.dp)
                ProductInfo(
                    info = stringResource(R.string.energy),
                    value = product.energy,
                    measureUnit = stringResource(id = R.string.kilo_calories)
                )
                Divider(thickness = 1.dp)
                ProductInfo(
                    info = stringResource(R.string.proteins),
                    value = product.proteins,
                    measureUnit = product.measureUnit
                )
                Divider(thickness = 1.dp)
                ProductInfo(
                    info = stringResource(R.string.fats),
                    value = product.fats,
                    measureUnit = product.measureUnit
                )
                Divider(thickness = 1.dp)
                ProductInfo(
                    info = stringResource(R.string.carbohydrates),
                    value = product.energy,
                    measureUnit = product.measureUnit
                )
                Divider(thickness = 1.dp)
            }
        }
    }
}

@Composable
fun ProductInfo(info: String, value: Double, measureUnit: String, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.padding(
            horizontal = dimensionResource(id = R.dimen.padding_large),
            vertical = dimensionResource(id = R.dimen.padding_medium)
        )
    ) {
        Text(
            text = info,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = "${value.toString().removeSuffix("0").removeSuffix(".")} $measureUnit",
            style = MaterialTheme.typography.titleSmall
        )
    }
}