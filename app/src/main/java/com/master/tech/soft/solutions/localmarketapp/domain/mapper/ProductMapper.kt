package com.master.tech.soft.solutions.localmarketapp.domain.mapper

import com.master.tech.soft.solutions.localmarketapp.data.model.Product
import com.master.tech.soft.solutions.localmarketapp.data.remote.dto.ProductDto

fun ProductDto.toDomain() = Product(
    id = id,
    name = name,
    description = description,
    price = price,
    imageUrl = imageUrl,
    category = category,
    inStock = inStock
)
