package com.example.emtyapp.data.entities

import java.util.Date
import com.google.firebase.Timestamp

data class Order(
    val id: String,
    val userId: String,
    val products: List<OrderItem>,
    val date: Timestamp, // Changed from Date to Timestamp
    val status: OrderStatus,
    val deliveryDay: Int // 1-4
)

data class OrderItem(
    val productId: String,
    val quantity: Int,
    val priceAtPurchase: Double
)

enum class OrderStatus {
    PROCESSING, SENT, DELIVERED
}