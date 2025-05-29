package com.example.emtyapp.data.entities

import com.google.gson.annotations.SerializedName

data class Product(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("imageRes")
    val imageRes: String,
    @SerializedName("category")
    val category: String,
    @SerializedName("price")
    val price: String,
    @SerializedName("quantity")
    val quantity: Int,
    @SerializedName("description")
    val description: String
)