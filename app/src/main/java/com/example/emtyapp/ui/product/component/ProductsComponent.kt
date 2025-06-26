package com.example.emtyapp.ui.product.component

import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.style.TextAlign
import com.example.emtyapp.ui.product.ProductViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductListScreen(
    products: List<Product>,
    onProductClick: (String) -> Unit,
    onLogout: () -> Unit,
    viewModel: ProductViewModel // Add viewModel parameter
) {
    // Get all categories from the original products list, not the filtered ones
    val allCategories by remember { derivedStateOf {
        viewModel.state.value.products.map { it.category }.distinct()
    } }
    val selectedCategory by viewModel.selectedCategory.collectAsState()

    Scaffold(
        topBar = {
            Column(modifier = Modifier.fillMaxWidth()) {
                // Action buttons row - now with left-aligned profile/cart and right-aligned logout
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Left-aligned buttons
                    Row(
                        modifier = Modifier.weight(1f),
                        horizontalArrangement = Arrangement.Start
                    ) {
                        // Profile button
                        IconButton(
                            onClick = { /* TODO */ },
                            modifier = Modifier.size(48.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.AccountCircle,
                                contentDescription = "Profile"
                            )
                        }

                        // Cart button
                        IconButton(
                            onClick = { /* TODO */ },
                            modifier = Modifier.size(48.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.ShoppingCart,
                                contentDescription = "Cart"
                            )
                        }
                    }

                    // Right-aligned logout button
                    IconButton(
                        onClick = onLogout,
                        modifier = Modifier.size(48.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.ExitToApp,
                            contentDescription = "Logout"
                        )
                    }
                }

                // Title with added spacing below
                Text(
                    text = "Our Products",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .padding(bottom = 8.dp),
                    textAlign = TextAlign.Left
                )

                // Category filter chips
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState())
                        .padding(horizontal = 16.dp)
                        .padding(bottom = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // "All" filter
                    FilterChip(
                        selected = selectedCategory == null,
                        onClick = { viewModel.setCategoryFilter(null) },
                        label = { Text("All") },  // Added label parameter
                        modifier = Modifier.padding(end = 8.dp)
                    )

                    // Category filters
                    allCategories.forEach { category ->
                        FilterChip(
                            selected = selectedCategory == category,
                            onClick = { viewModel.setCategoryFilter(category) },
                            label = { Text(category) }  // Added label parameter
                        )
                    }
                }
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
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