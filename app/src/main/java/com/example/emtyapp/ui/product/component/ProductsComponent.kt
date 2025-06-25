package com.example.emtyapp.ui.product.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.emtyapp.data.entities.Product

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductListScreen(
    products: List<Product>,
    onProductClick: (String) -> Unit,
    onLogout: () -> Unit // Add logout callback
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Button(
                            onClick = onLogout,
                            modifier = Modifier.padding(bottom = 8.dp)
                        ) {
                            Text("Logout")
                        }
                        Text("Our Products")
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            items(products) { product ->
                ProductItem(
                    product = product,
                    onClick = { onProductClick(product.id) }
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}