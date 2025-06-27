package com.example.emtyapp.ui.cart.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.emtyapp.data.entities.Product
import com.example.emtyapp.ui.cart.CartViewModel
import androidx.compose.runtime.getValue
import com.example.emtyapp.navigator.Routes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(
    navController: NavController,
    viewModel: CartViewModel,
    onLogout: () -> Unit
) {
    val cartItems by viewModel.cartItems.collectAsState()
    val totalPrice = viewModel.getTotalPrice()

    Scaffold(
        topBar = {
            Column(modifier = Modifier.fillMaxWidth()) {
                // Action buttons row - now with left-aligned home/profile and right-aligned logout
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
                        // Home button
                        IconButton(
                            onClick = { navController.navigate(Routes.Home) },
                            modifier = Modifier.size(48.dp)
                        ) {
                            Icon(
                                Icons.Default.Home,
                                contentDescription = "Home"
                            )
                        }

                        // Profile button
                        IconButton(
                            onClick = { navController.navigate(Routes.Profile) },
                            modifier = Modifier.size(48.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.AccountCircle,
                                contentDescription = "Profile"
                            )
                        }
                    }

                    // Right-aligned logout button
                    IconButton(
                        onClick = onLogout,
                        modifier = Modifier.size(48.dp)
                    ) {
                        Icon(
                            Icons.Default.ExitToApp,
                            contentDescription = "Logout"
                        )
                    }
                }

                // Cart title
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Your Cart",
                        style = MaterialTheme.typography.headlineSmall,
                        textAlign = TextAlign.Center
                    )
                }
            }
        },
        bottomBar = {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Total:", fontWeight = FontWeight.Bold)
                    Text("$${"%.2f".format(totalPrice)}")
                }
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = { /* Handle checkout */ },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = cartItems.isNotEmpty()
                ) {
                    Text("Checkout")
                }
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            if (cartItems.isEmpty()) {
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 32.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            Icons.Default.ShoppingCart,
                            contentDescription = "Empty Cart",
                            modifier = Modifier.size(64.dp)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Your cart is empty",
                            style = MaterialTheme.typography.titleLarge,
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = "Browse our products and add some items!",
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = { navController.navigate(Routes.Home) }
                        ) {
                            Text("Continue Shopping")
                        }
                    }
                }
            } else {
                items(cartItems.entries.toList()) { (product, quantity) ->
                    CartItem(
                        product = product,
                        quantity = quantity,
                        onAdd = { viewModel.addToCart(product) },
                        onRemove = { viewModel.removeFromCart(product) }
                    )
                    Divider()
                }
            }
        }
    }
}

@Composable
fun CartItem(
    product: Product,
    quantity: Int,
    onAdd: () -> Unit,
    onRemove: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(product.name, fontWeight = FontWeight.Bold)
            Text("$${product.price}")
        }
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onRemove) {
                Text("-")
            }
            Text("$quantity")
            IconButton(onClick = onAdd) {
                Text("+")
            }
        }
    }
}