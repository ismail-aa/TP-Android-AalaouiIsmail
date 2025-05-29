package com.example.emtyapp.data.entities

import com.google.gson.annotations.SerializedName

data class Product(
    val id: String,
    val name: String,
    val imageRes: String,
    val category: String,
    val price: String,
    val quantity: Int,
    val description: String
)