package com.example.emtyapp.ui.product

import com.example.emtyapp.data.entities.Product

data class  ProductViewState(
    val isLoading: Boolean = false,
    val products: List<Product> = emptyList(),
    val filteredProducts: List<Product> = products,
    val error: String? = null
)