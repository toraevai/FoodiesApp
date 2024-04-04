package com.example.foodies.data

import com.example.foodies.model.Product
import com.example.foodies.model.ProductCategory
import com.example.foodies.model.ProductTag
import com.example.foodies.network.FoodiesApiService
import javax.inject.Inject

class FoodiesRepository @Inject constructor(private val foodiesApiService: FoodiesApiService) {
    suspend fun getCategories(): List<ProductCategory> = foodiesApiService.getCategories()

    suspend fun getTags(): List<ProductTag> = foodiesApiService.getTags()

    suspend fun getProducts(): List<Product> = foodiesApiService.getProducts()
}