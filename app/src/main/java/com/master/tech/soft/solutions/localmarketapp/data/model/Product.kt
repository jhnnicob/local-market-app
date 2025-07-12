package com.master.tech.soft.solutions.localmarketapp.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Product(
    @Json(name = "id")
    val id: String,

    @Json(name = "name")
    val name: String,

    @Json(name = "description")
    val description: String? = null,

    @Json(name = "price")
    val price: Double,

    @Json(name = "imageUrl")
    val imageUrl: String? = null,

    @Json(name = "category")
    val category: String? = null,

    @Json(name = "inStock")
    val inStock: Boolean = true
)