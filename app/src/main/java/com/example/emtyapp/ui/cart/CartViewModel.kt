package com.example.emtyapp.ui.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.emtyapp.data.entities.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CartViewModel : ViewModel() {
    private val _cartItems = MutableStateFlow<Map<Product, Int>>(emptyMap())
    val cartItems: StateFlow<Map<Product, Int>> = _cartItems.asStateFlow()

    fun addToCart(product: Product) {
        viewModelScope.launch {
            val currentItems = _cartItems.value.toMutableMap()
            currentItems[product] = currentItems.getOrDefault(product, 0) + 1
            _cartItems.value = currentItems
        }
    }

    fun removeFromCart(product: Product) {
        viewModelScope.launch {
            val currentItems = _cartItems.value.toMutableMap()
            val currentQuantity = currentItems[product] ?: 0
            if (currentQuantity > 1) {
                currentItems[product] = currentQuantity - 1
            } else {
                currentItems.remove(product)
            }
            _cartItems.value = currentItems
        }
    }

    fun clearCart() {
        viewModelScope.launch {
            _cartItems.value = emptyMap()
        }
    }

    fun getTotalPrice(): Double {
        return _cartItems.value.entries.sumOf { (product, quantity) ->
            product.price.toDoubleOrNull() ?: 0.0 * quantity
        }
    }
}