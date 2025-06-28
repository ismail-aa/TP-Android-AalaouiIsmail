package com.example.emtyapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import com.example.emtyapp.navigator.AppNavigation
import com.example.emtyapp.ui.auth.AuthViewModel
import com.example.emtyapp.ui.product.ProductViewModel
import com.example.emtyapp.ui.theme.EmtyAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val authViewModel: AuthViewModel by viewModels()
    private val productViewModel: ProductViewModel by viewModels()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EmtyAppTheme {
                Surface {
                    AppNavigation(authViewModel, productViewModel)
                }
            }
        }
    }
}