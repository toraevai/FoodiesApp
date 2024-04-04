package com.example.foodies.model

import kotlinx.serialization.Serializable

@Serializable
data class ProductTag(
    val id: Int,
    val name: String
)

val fakeTag = ProductTag(
    id = 2,
    name = "Вегетарианское блюдо"
)