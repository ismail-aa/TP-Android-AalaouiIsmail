package com.example.emtyapp.ui.product

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.emtyapp.data.repository.ProductRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProductViewModel(private val repository: ProductRepository) : ViewModel() {
    private val _state = MutableStateFlow(ProductViewState())
    val state: StateFlow<ProductViewState> = _state

    class Factory(private val repository: ProductRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ProductViewModel(repository) as T
        }
    }

    fun handleIntent(intent: ProductIntent) {
        when (intent) {
            ProductIntent.LoadProducts -> loadProducts()
        }
    }

    private fun loadProducts() = viewModelScope.launch {
        _state.value = _state.value.copy(isLoading = true, error = null)
        try {
            val products = repository.getProducts()
            _state.value = ProductViewState(products = products)
        } catch (e: Exception) {
            _state.value = ProductViewState(
                error = e.message ?: "Failed to load products"
            )
        }
    }
}