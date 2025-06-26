package com.example.emtyapp.ui.product

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.emtyapp.data.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.combine

@HiltViewModel
class ProductViewModel @Inject constructor(private val repository: ProductRepository) : ViewModel() {
    private val _state = MutableStateFlow(ProductViewState())
    val state: StateFlow<ProductViewState> = _state

    private var _selectedCategory = MutableStateFlow<String?>(null)
    val selectedCategory: StateFlow<String?> = _selectedCategory

    init {
        viewModelScope.launch {
            combine(
                _state,
                _selectedCategory
            ) { state, category ->
                state.copy(
                    filteredProducts = if (category == null) {
                        state.products
                    } else {
                        state.products.filter { it.category == category }
                    }
                )
            }.collect { newState ->
                _state.value = newState
            }
        }
    }

    fun handleIntent(intent: ProductIntent) {
        when (intent) {
            ProductIntent.LoadProducts -> loadProducts()
        }
    }

    fun setCategoryFilter(category: String?) {
        _selectedCategory.value = category
        _state.value = _state.value.copy(
            filteredProducts = if (category == null) {
                _state.value.products
            } else {
                _state.value.products.filter { it.category == category }
            }
        )
    }

    private fun loadProducts() = viewModelScope.launch {
        _state.value = _state.value.copy(isLoading = true, error = null)
        try {
            Log.d("products repo", "loadProducts")
            val products = repository.getProducts()
            _state.value = ProductViewState(isLoading = false, products = products)
        } catch (e: Exception) {
            Log.d("products repo", "Exception")
            _state.value = ProductViewState(
                isLoading = false, error = e.message ?: "Failed to load products"
            )
        }
    }
}