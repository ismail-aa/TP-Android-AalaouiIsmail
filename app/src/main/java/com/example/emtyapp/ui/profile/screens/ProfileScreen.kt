package com.example.emtyapp.ui.profile.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.emtyapp.ui.profile.ProfileIntent
import com.example.emtyapp.ui.profile.ProfileViewModel
import com.example.emtyapp.ui.profile.component.OrderTrackingComponent
import com.example.emtyapp.ui.profile.component.StockManagementComponent
import com.example.emtyapp.ui.profile.component.UserInfoComponent
import com.example.emtyapp.ui.profile.component.UserManagementComponent
import androidx.compose.runtime.getValue
import androidx.compose.ui.text.style.TextAlign
import com.example.emtyapp.navigator.Routes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel,
    navController: NavController,
    onLogout: () -> Unit
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.handleIntent(ProfileIntent.LoadUserData)
    }

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
                            onClick = { navController.navigate(Routes.Home) },
                            modifier = Modifier.size(48.dp)
                        ) {
                            Icon(
                                Icons.Default.Home,
                                contentDescription = "Profile"
                            )
                        }

                        // Cart button
                        IconButton(
                            onClick = { /* TODO */ },
                            modifier = Modifier.size(48.dp)
                        ) {
                            Icon(
                                Icons.Default.ShoppingCart,
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
                            Icons.Default.ExitToApp,
                            contentDescription = "Logout"
                        )
                    }
                }

                Text(
                    text = "Profile",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .padding(bottom = 8.dp),
                    textAlign = TextAlign.Left
                )
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            when {
                state.isLoading -> {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }
                state.error != null -> {
                    item {
                        Text(
                            text = state.error ?: "Error",
                            color = MaterialTheme.colorScheme.error,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }
                state.user != null -> {
                    val user = state.user!!

                    // User Info Section
                    item {
                        UserInfoComponent(
                            user = user,
                            viewModel = viewModel,
                            navController = navController
                        )
                    }

                    item { Spacer(modifier = Modifier.height(16.dp)) }

                    // Order Tracking Section
                    item {
                        OrderTrackingComponent(
                            orders = emptyList(),
                            navController = navController,
                            viewModel = viewModel
                        )
                    }

                    // Conditionally show management sections based on role
                    if (user.role == "gerant" || user.role == "admin") {
                        item {
                            StockManagementComponent(
                                products = emptyList(),
                                navController = navController,
                                viewModel = viewModel
                            )
                        }
                    }

                    if (user.role == "admin") {
                        item {
                            UserManagementComponent(
                                users = emptyList(),
                                navController = navController,
                                viewModel = viewModel
                            )
                        }
                    }

                    item { Spacer(modifier = Modifier.height(32.dp)) }
                }
            }
        }
    }
}