package com.example.emtyapp.ui.profile.component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.emtyapp.data.entities.Product
import com.example.emtyapp.ui.profile.ProfileViewModel
import androidx.compose.foundation.lazy.items

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StockManagementComponent(
    products: List<Product>,
    navController: NavController,
    viewModel: ProfileViewModel
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /* Navigate to add product */ },
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(Icons.Default.Add, "Add Product")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            Text(
                text = "Stock Management",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(16.dp)
            )

            LazyColumn {
                items(products) { product ->
                    ProductStockItem(product) {
                        // Handle edit
                    }
                    Divider()
                }
            }
        }
    }
}

@Composable
private fun ProductStockItem(product: Product, onEdit: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(product.name, fontWeight = FontWeight.Bold)
            Text("Stock: ${product.quantity}")
        }
        Button(onClick = onEdit) {
            Text("Edit")
        }
    }
}