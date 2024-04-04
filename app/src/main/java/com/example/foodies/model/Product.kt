package com.example.foodies.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Product(
    val id: Int,
    @SerialName("category_id") val categoryId: Int,
    val name: String,
    val description: String,
    val image: String,
    @SerialName("price_current") val priceCurrent: Int,
    @SerialName("price_old") val priceOld: Int?,
    val measure: Int,
    @SerialName("measure_unit") val measureUnit: String,
    @SerialName("energy_per_100_grams") val energy: Double,
    @SerialName("proteins_per_100_grams") val proteins: Double,
    @SerialName("fats_per_100_grams") val fats: Double,
    @SerialName("carbohydrates_per_100_grams") val carbohydrates: Double,
    @SerialName("tag_ids") val tagIds: List<Int>
)

val fakeProduct: Product = Product(
    id = 1,
    categoryId = 676153,
    name = "Авокадо Кранч Маки 8шт",
    description = "Ролл с нежным мясом камчатского краба, копченой курицей и авокадо.Украшается соусом\"Унаги\" и легким майонезом  Комплектуется бесплатным набором для роллов (Соевый соус Лайт 35г., васаби 6г., имбирь 15г.). +1 набор за каждые 600 рублей в заказе",
    image = "1.jpg",
    priceCurrent = 47000,
    priceOld = null,
    measure = 250,
    measureUnit = "г",
    energy = 307.8,
    proteins = 6.1,
    fats = 11.4,
    carbohydrates = 45.1,
    tagIds = listOf(2)
)