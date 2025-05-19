package com.example.emtyapp.data.repository

import com.example.emtyapp.R
import com.example.emtyapp.data.entities.Product
import jakarta.inject.Inject

class ProductRepository @Inject constructor() {
    fun getProducts(): List<Product> {
        return listOf(
            Product(
                id = "TAB001",
                name = "Tablette SAM 12 Pouce",
                imageRes = R.drawable.tablet,
                category = "Tablette",
                price = "2334 DHS",
                quantity = 15,
                description = "High-performance tablet with 12-inch display"
            ),
            Product(
                id = "PHN001",
                name = "IPHONE 14 PRO",
                imageRes = R.drawable.iphone,
                category = "Iphone",
                price = "13000 DHS",
                quantity = 5,
                description = "Latest iPhone with advanced camera system"
            ),
            Product(
                id = "TV001",
                name = "SMART TV 42 P",
                imageRes = R.drawable.tv,
                category = "TV",
                price = "4000 DHS",
                quantity = 20,
                description = "42-inch smart TV with 4K resolution"
            ),
            Product(
                id = "LAP001",
                name = "Laptop Dell XPS",
                imageRes = R.drawable.laptop,
                category = "Laptop",
                price = "9000 DHS",
                quantity = 5,
                description = "Premium laptop with high-end specifications"
            ),
            Product(
                id = "HP001",
                name = "Wireless Headphones",
                imageRes = R.drawable.headphone,
                category = "Headphones",
                price = "500 DHS",
                quantity = 0,
                description = "Noise-cancelling wireless headphones"
            )
        )
    }
}