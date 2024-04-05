package com.example.foodies.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import com.example.foodies.model.ProductCategory
import com.example.foodies.model.ProductTag
import com.example.foodies.model.fakeCategory
import com.example.foodies.model.fakeProduct
import com.example.foodies.model.fakeTag
import com.example.foodies.navigation.NavigationDestination
import com.example.foodies.ui.parts.Counter
import com.example.foodies.ui.parts.FoodiesAppBotAppBar
import com.example.foodies.ui.parts.ProductImageWithTag
import com.example.foodies.ui.theme.FoodiesTheme
import java.text.NumberFormat

object CatalogScreenDestination : NavigationDestination {
    override val route = "catalog_screen"
}

@Composable
fun CatalogScreen(
    catalogScreenUiState: CatalogScreenUiState,
    goToCartScreen: () -> Unit,
    onCardClick: (Product) -> Unit,
    modifier: Modifier = Modifier
) {
    when (catalogScreenUiState) {
        is CatalogScreenUiState.Loading -> {}
        is CatalogScreenUiState.Success -> {
            SuccessCatalogScreen(
                currentCategory = catalogScreenUiState.currentCategory.value,
                categoriesOfProduct = catalogScreenUiState.categoriesOfProduct,
                changeCategory = { catalogScreenUiState.changeCategory(it) },
                productsList = catalogScreenUiState.productsList,
                cart = catalogScreenUiState.cart.value,
                listOfTags = catalogScreenUiState.listOfTags,
                increaseCounter = { catalogScreenUiState.increaseCounter(it) },
                decreaseCounter = { catalogScreenUiState.decreaseCounter(it) },
                goToCartScreen = goToCartScreen,
                onCardClick = onCardClick,
                modifier = modifier
            )
        }

        is CatalogScreenUiState.Error -> {}
    }
}

@Composable
fun SuccessCatalogScreen(
    currentCategory: ProductCategory,
    categoriesOfProduct: List<ProductCategory>,
    changeCategory: (ProductCategory) -> Unit,
    productsList: List<Product>,
    cart: Map<Product, Int>,
    listOfTags: List<ProductTag>,
    increaseCounter: (Product) -> Unit,
    decreaseCounter: (Product) -> Unit,
    goToCartScreen: () -> Unit,
    onCardClick: (Product) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            CatalogScreenTopAppBar(onFilterClick = {}, onSearchClick = {})
        },
        bottomBar = {
            if (cart.filter { it.value != 0 }.isNotEmpty()) {
                FoodiesAppBotAppBar(
                    onButtonClick = goToCartScreen,
                    cart = cart,
                    currentDestination = CatalogScreenDestination,
                    currentProduct = fakeProduct
                )
            }
        }
    ) { paddingValues ->
        Column(modifier = modifier.padding(paddingValues)) {
            ListOfProductCategories(
                currentCategory = currentCategory,
                productCategories = categoriesOfProduct,
                changeCurrentCategory = { changeCategory(it) }
            )
            GridOfProducts(
                productsList = productsList.filter { it.categoryId == currentCategory.id },
                cart = cart,
                listOfTags = listOfTags,
                increaseCounter = increaseCounter,
                decreaseCounter = decreaseCounter,
                onCardClick = onCardClick,
                modifier = Modifier.padding(all = dimensionResource(id = R.dimen.padding_large))
            )
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun CatalogScreenTopAppBar(
    onFilterClick: () -> Unit, onSearchClick: () -> Unit
) {
    CenterAlignedTopAppBar(title = {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = stringResource(R.string.logo),
            modifier = Modifier.height(60.dp),
            contentScale = ContentScale.Crop
        )
    }, navigationIcon = {
        IconButton(onClick = onFilterClick) {
            Icon(
                painter = painterResource(id = R.drawable.filter),
                contentDescription = stringResource(R.string.menu),
                modifier = Modifier.size(24.dp)
            )
        }
    }, actions = {
        IconButton(onClick = onSearchClick) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = stringResource(R.string.search),
                modifier = Modifier.size(24.dp)
            )
        }
    })
}

@Composable
fun ListOfProductCategories(
    currentCategory: ProductCategory,
    productCategories: List<ProductCategory>,
    changeCurrentCategory: (ProductCategory) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier,
        contentPadding = PaddingValues(
            start = dimensionResource(id = R.dimen.padding_large),
            end = dimensionResource(id = R.dimen.padding_large)
        ),
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_small))
    ) {
        items(productCategories) { productCategory ->
            Button(
                onClick = { changeCurrentCategory(productCategory) },
                modifier = Modifier.height(40.dp),
                shape = MaterialTheme.shapes.medium,
                colors = if (productCategory == currentCategory) ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ) else ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondary
                )
            ) {
                Text(
                    text = productCategory.name,
                    modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.padding_large))
                )
            }
        }
    }
}

@Composable
fun GridOfProducts(
    productsList: List<Product>,
    cart: Map<Product, Int>,
    listOfTags: List<ProductTag>,
    increaseCounter: (Product) -> Unit,
    decreaseCounter: (Product) -> Unit,
    onCardClick: (Product) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_small)),
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_small))
    ) {
        items(productsList) { product ->
            GridProductCard(
                product = product,
                productCount = cart[product]!!,
                listOfTags = listOfTags,
                increaseCounter = { increaseCounter(product) },
                decreaseCounter = { decreaseCounter(product) },
                onCardClick = { onCardClick(product) }
            )
        }
    }
}

@Composable
fun GridProductCard(
    product: Product,
    productCount: Int,
    listOfTags: List<ProductTag>,
    increaseCounter: () -> Unit,
    decreaseCounter: () -> Unit,
    onCardClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(modifier = modifier
        .fillMaxWidth()
        .clickable { onCardClick() }
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.heightIn(min = 290.dp)
        ) {
            ProductImageWithTag(
                product = product,
                listOfTags = listOfTags,
                currentDestination = CatalogScreenDestination,
                onBackClick = { },
                modifier = Modifier.height(170.dp)
            )
            Text(
                text = product.name,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(
                    start = dimensionResource(id = R.dimen.padding_medium),
                    end = dimensionResource(id = R.dimen.padding_medium)
                ),
                minLines = 2
            )
            Text(
                text = "${product.measure} ${product.measureUnit}",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(
                    start = dimensionResource(id = R.dimen.padding_small)
                )
            )
            Box(contentAlignment = Alignment.Center) {
                if (productCount == 0) {
                    Button(
                        onClick = increaseCounter,
                        shape = MaterialTheme.shapes.medium,
                        modifier = Modifier
                            .padding(
                                start = dimensionResource(id = R.dimen.padding_medium),
                                bottom = dimensionResource(id = R.dimen.padding_medium),
                                end = dimensionResource(id = R.dimen.padding_medium)
                            )
                            .fillMaxWidth()
                    ) {
                        Row {
                            Text(
                                text = "${product.priceCurrent / 100} ${NumberFormat.getCurrencyInstance().currency!!.symbol}",
                                style = MaterialTheme.typography.titleSmall
                            )
                            if (product.priceOld != null) {
                                Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.padding_small)))
                                Text(
                                    text = "${product.priceOld / 100} ${NumberFormat.getCurrencyInstance().currency!!.symbol}",
                                    style = MaterialTheme.typography.bodyMedium,
                                    textDecoration = TextDecoration.LineThrough
                                )
                            }
                        }
                    }
                } else {
                    Counter(
                        counter = productCount,
                        onAddClick = increaseCounter,
                        onRemoveClick = decreaseCounter,
                        modifier = Modifier
                            .padding(
                                start = dimensionResource(id = R.dimen.padding_medium),
                                bottom = dimensionResource(id = R.dimen.padding_medium),
                                end = dimensionResource(id = R.dimen.padding_medium)
                            )
                            .fillMaxWidth()
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun SuccessCatalogScreenPreview() {
    FoodiesTheme {
        SuccessCatalogScreen(
            currentCategory = fakeCategory,
            categoriesOfProduct = listOf(fakeCategory, fakeCategory, fakeCategory),
            changeCategory = {},
            productsList = listOf(fakeProduct, fakeProduct, fakeProduct, fakeProduct),
            cart = mutableMapOf(fakeProduct to 1),
            increaseCounter = {},
            decreaseCounter = {},
            goToCartScreen = {},
            onCardClick = {},
            listOfTags = listOf(fakeTag)
        )
    }
}

@Preview
@Composable
fun GridProductCardPreview() {
    FoodiesTheme {
        GridProductCard(
            product = fakeProduct,
            productCount = 0,
            listOfTags = listOf(ProductTag(2, "Вегетарианское блюдо")),
            increaseCounter = {},
            decreaseCounter = {},
            onCardClick = {}
        )
    }
}

@Preview
@Composable
fun CatalogScreenTopAppBarPreview() {
    FoodiesTheme {
        CatalogScreenTopAppBar(onFilterClick = {}, onSearchClick = {})
    }
}

@Preview
@Composable
fun CatalogScreenBottomAppBarPreview() {
    FoodiesTheme {
        FoodiesAppBotAppBar(
            onButtonClick = {},
            cart = mapOf(fakeProduct to 1),
            currentDestination = CatalogScreenDestination,
            currentProduct = fakeProduct
        )
    }
}