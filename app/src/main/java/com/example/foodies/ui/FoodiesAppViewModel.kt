package com.example.foodies.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodies.data.FoodiesRepository
import com.example.foodies.model.Product
import com.example.foodies.model.ProductCategory
import com.example.foodies.model.ProductTag
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class FoodiesAppViewModel @Inject constructor(private val foodiesRepository: FoodiesRepository) :
    ViewModel() {

    var catalogScreenUiState: CatalogScreenUiState by mutableStateOf(CatalogScreenUiState.Loading)
        private set

    init {
        loadCategories()
    }

    private fun loadCategories() {
        viewModelScope.launch {
            catalogScreenUiState = CatalogScreenUiState.Loading
            catalogScreenUiState = try {
                val productsList = foodiesRepository.getProducts()
                val categoriesOfProduct = foodiesRepository.getCategories()
                CatalogScreenUiState.Success(
                    categoriesOfProduct = categoriesOfProduct,
                    productsList = productsList,
                    listOfTags = foodiesRepository.getTags(),
                    cart = mutableStateOf(productsList.associateWith { 0 }),
                    currentCategory = mutableStateOf(categoriesOfProduct[0])
                )
            } catch (e: IOException) {
                CatalogScreenUiState.Error
            } catch (e: HttpException) {
                CatalogScreenUiState.Error
            }
        }
    }


}

sealed interface CatalogScreenUiState {
    class Success(
        val categoriesOfProduct: List<ProductCategory>,
        val productsList: List<Product>,
        val listOfTags: List<ProductTag>,
        var cart: MutableState<Map<Product, Int>>,
        var currentCategory: MutableState<ProductCategory>
    ) : CatalogScreenUiState {
        fun changeCategory(category: ProductCategory) {
            currentCategory.value = category
        }

        fun increaseCounter(product: Product) {
            val currentCounter = cart.value[product]!!
            val newMap = cart.value.toMutableMap()
            newMap[product] = currentCounter + 1
            cart.value = newMap
        }

        fun decreaseCounter(product: Product) {
            val currentCounter = cart.value[product]!!
            val newMap = cart.value.toMutableMap()
            newMap[product] = currentCounter - 1
            cart.value = newMap
        }
    }

    data object Error : CatalogScreenUiState
    data object Loading : CatalogScreenUiState
}