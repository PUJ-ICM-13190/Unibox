package com.co.unibox.data

data class Product(
    val id: String,
    val name: String = "",
    val category: String = "",
    val quantity: Int? = 0,
    val price: Double = 0.0,
    val description: String = "",
    val imageUrl: String = ""
)
