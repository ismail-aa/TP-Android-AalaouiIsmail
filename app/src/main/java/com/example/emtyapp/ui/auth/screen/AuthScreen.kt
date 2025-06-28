package com.example.emtyapp.ui.auth.screen

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.emtyapp.ui.auth.AuthViewModel
import com.example.emtyapp.ui.auth.component.LoginScreen
import com.example.emtyapp.ui.auth.component.RegisterScreen
import com.example.emtyapp.ui.auth.AuthEvent
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

@Composable
fun AuthScreen(
    viewModel: AuthViewModel,
    onAuthSuccess: () -> Unit,
    navController: NavController
) {
    val showLoginForm by viewModel.showLoginForm.collectAsState()

    if (showLoginForm) {
        LoginScreen(
            viewModel = viewModel,
            onLoginSuccess = onAuthSuccess,
            onNavigateToRegister = {
                viewModel.handleEvent(AuthEvent.NavigateToRegister)
            }
        )
    } else {
        RegisterScreen(
            viewModel = viewModel,
            onRegisterSuccess = onAuthSuccess,
            onNavigateToLogin = {
                viewModel.handleEvent(AuthEvent.NavigateToLogin)
            }
        )
    }
}