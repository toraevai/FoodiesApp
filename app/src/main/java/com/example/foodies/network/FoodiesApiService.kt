package com.example.foodies.network

import com.example.foodies.model.Product
import com.example.foodies.model.ProductCategory
import com.example.foodies.model.ProductTag
import retrofit2.http.GET

interface FoodiesApiService {

    @GET("Categories.json")
    suspend fun getCategories(): List<ProductCategory>

    @GET("Tags.json")
    suspend fun getTags(): List<ProductTag>

    @GET("Products.json")
    suspend fun getProducts(): List<Product>
}