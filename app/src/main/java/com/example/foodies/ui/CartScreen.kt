package com.example.foodies.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.foodies.R
import com.example.foodies.model.Product
import com.example.foodies.model.fakeProduct
import com.example.foodies.navigation.NavigationDestination
import com.example.foodies.ui.parts.Counter
import com.example.foodies.ui.parts.FoodiesAppBotAppBar
import com.example.foodies.ui.theme.FoodiesTheme
import com.example.foodies.ui.theme.Orange
import java.text.NumberFormat

object CartScreenDestination : NavigationDestination {
    override val route = "cart_screen"
}

@Composable
fun CartScreen(
    catalogScreenUiState: CatalogScreenUiState, onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    when (catalogScreenUiState) {
        is CatalogScreenUiState.Success -> SuccessCartScreen(
            cart = catalogScreenUiState.cart.value.filter { it.value != 0 },
            onBackClick = onBackClick,
            modifier = modifier
        )

        else -> {}
    }
}

@Composable
fun SuccessCartScreen(
    cart: Map<Product, Int>,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            SuccessCartScreenTopAppBar(onBackClick = onBackClick)
        },
        bottomBar = {
            FoodiesAppBotAppBar(
                onButtonClick = { /*TODO*/ },
                cart = cart,
                currentDestination = CartScreenDestination,
                currentProduct = fakeProduct
            )
        }
    ) { paddingValues ->
        LazyColumn(modifier = modifier.padding(paddingValues)) {
            item { Divider(thickness = 1.dp) }
            for (pair in cart) {
                item {
                    CartItem(
                        product = pair.key,
                        counter = pair.value,
                        onAddClick = { /*TODO*/ },
                        onRemoveClick = { /*TODO*/ })
                    Divider(thickness = 1.dp)
                }
            }
        }
    }
}

@Composable
fun CartItem(
    product: Product,
    counter: Int,
    onAddClick: () -> Unit,
    onRemoveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.padding(dimensionResource(id = R.dimen.padding_large)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.photo),
            contentDescription = stringResource(R.string.photo, product.name),
            modifier = Modifier.size(96.dp),
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier
                .height(98.dp)
                .padding(start = dimensionResource(id = R.dimen.padding_large))
        ) {
            Text(
                text = product.name,
                style = MaterialTheme.typography.bodySmall,
                minLines = 2
            )
            Spacer(modifier = Modifier.weight(1f))
            Row {
                Counter(
                    counter = counter,
                    onAddClick = onAddClick,
                    onRemoveClick = onRemoveClick,
                    modifier = Modifier.width(135.dp)
                )
                Spacer(modifier = Modifier.weight(1f))
                Column {
                    Text(
                        text = "${product.priceCurrent / 100} ${NumberFormat.getCurrencyInstance().currency!!.symbol}",
                        style = MaterialTheme.typography.titleSmall
                    )
                    if (product.priceOld != null) Text(
                        text = "${product.priceOld / 100} ${NumberFormat.getCurrencyInstance().currency!!.symbol}",
                        style = MaterialTheme.typography.bodyMedium,
                        textDecoration = TextDecoration.LineThrough
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SuccessCartScreenTopAppBar(onBackClick: () -> Unit) {
    TopAppBar(
        title = {
            Text(
                text = stringResource(R.string.cart),
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(start = dimensionResource(id = R.dimen.padding_large))
            )
        },
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = stringResource(R.string.go_back),
                    modifier = Modifier.size(24.dp),
                    tint = Orange
                )
            }
        }
    )
}

@Preview
@Composable
fun SuccessCartScreenPreview() {
    FoodiesTheme {
        SuccessCartScreen(cart = mapOf(fakeProduct to 1), onBackClick = {})
    }
}