package com.example.emtyapp.data.repository

import android.util.Log
import com.example.emtyapp.R
import com.example.emtyapp.data.Api.ProductApi
import com.example.emtyapp.data.entities.Product
import jakarta.inject.Inject

class ProductRepository @Inject constructor(private val api: ProductApi
) {
    suspend fun getProducts(): List<Product> {
        // fetch data from a remote server
        val products = api.getProducts()
        Log.d("products repo", "size :"+ products.size)
        return products
    }
}