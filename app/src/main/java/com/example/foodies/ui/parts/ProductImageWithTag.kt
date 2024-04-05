package com.example.foodies.ui.parts

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.foodies.R
import com.example.foodies.model.Product
import com.example.foodies.model.ProductTag
import com.example.foodies.model.fakeProduct
import com.example.foodies.model.fakeTag
import com.example.foodies.navigation.NavigationDestination
import com.example.foodies.ui.CatalogScreenDestination
import com.example.foodies.ui.ProductCardDestination
import com.example.foodies.ui.theme.FoodiesTheme

@Composable
fun ProductImageWithTag(
    product: Product,
    listOfTags: List<ProductTag>,
    currentDestination: NavigationDestination,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box {
        ProductTags(
            product = product,
            listOfTags = listOfTags,
            currentDestination = currentDestination,
            onBackClick = onBackClick,
            modifier = Modifier
                .size(if (currentDestination == ProductCardDestination) 44.dp else 24.dp)
                .padding(
                    start = dimensionResource(id = R.dimen.padding_small),
                    top = dimensionResource(id = R.dimen.padding_small)
                )
        )
        Image(
            painter = painterResource(id = R.drawable.photo),
            contentDescription = stringResource(R.string.photo, product.name),
            modifier = modifier,
            contentScale = ContentScale.Crop
        )
    }
}


@Composable
fun ProductTags(
    product: Product,
    listOfTags: List<ProductTag>,
    currentDestination: NavigationDestination,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row {
        if (currentDestination == ProductCardDestination) IconButton(onClick = onBackClick) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = stringResource(R.string.go_back),
                modifier = modifier
            )
        }
        for (tagId in product.tagIds) {
            Image(
                painter = painterResource(
                    id = when (tagId) {
                        2 -> R.drawable.tag_without_meat
                        4 -> R.drawable.tag_spicy
                        else -> R.drawable.tag_discount
                    }
                ),
                contentDescription = listOfTags.find { it.id == tagId }!!.name,
                modifier = modifier
            )
        }
    }
}

@Preview
@Composable
fun ProductImageWithTagOnProductScreenPreview() {
    FoodiesTheme {
        ProductImageWithTag(
            product = fakeProduct,
            listOfTags = listOf(fakeTag, fakeTag),
            currentDestination = ProductCardDestination,
            onBackClick = {},
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview
@Composable
fun ProductImageWithTagOnCatalogPreview() {
    FoodiesTheme {
        ProductImageWithTag(
            product = fakeProduct,
            listOfTags = listOf(fakeTag, fakeTag),
            currentDestination = CatalogScreenDestination,
            onBackClick = {},
            modifier = Modifier.fillMaxWidth()
        )
    }
}