package com.example.foodies.model

import kotlinx.serialization.Serializable

@Serializable
data class ProductCategory(
    val id: Int,
    val name: String
)

val fakeCategory = ProductCategory(
    id = 676153,
    name = "Горячие блюда"
)