package com.example.emtyapp.ui.profile.component

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.emtyapp.data.entities.Order
import com.example.emtyapp.ui.profile.ProfileViewModel

@Composable
fun OrderTrackingComponent(
    orders: List<Order>,
    navController: NavController,
    viewModel: ProfileViewModel
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Order Tracking",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            if (orders.isEmpty()) {
                Text("No orders yet")
            } else {
                orders.forEach { order ->
                    OrderItem(order)
                    Divider()
                }
            }
        }
    }
}

@Composable
private fun OrderItem(order: Order) {
    Column(modifier = Modifier.padding(8.dp)) {
        Text("Order #${order.id}", fontWeight = FontWeight.Bold)
        Text("Status: ${order.status}")
        Text("Date: ${order.date}")
    }
}