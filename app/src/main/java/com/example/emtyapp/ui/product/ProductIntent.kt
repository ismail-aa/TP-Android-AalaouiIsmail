package com.example.emtyapp.ui.product

sealed class ProductIntent {
    object LoadProducts : ProductIntent()
}