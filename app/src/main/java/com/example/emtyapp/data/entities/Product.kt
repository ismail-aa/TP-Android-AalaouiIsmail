package com.example.emtyapp.data.entities

data class Product(
    val id: String,
    val name: String,
    val imageRes: Int,
    val category: String,
    val price: String,
    val quantity: Int,
    val description: String
)