package com.example.emtyapp.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.emtyapp.data.entities.User
import com.example.emtyapp.data.repository.AuthRepository
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {
    private val _state = MutableStateFlow<AuthState>(AuthState.Idle)
    val state: StateFlow<AuthState> = _state

    private val _showLoginForm = MutableStateFlow(true)
    val showLoginForm: StateFlow<Boolean> = _showLoginForm

    val currentUser: FirebaseUser? get() = repository.currentUser

    fun handleEvent(event: AuthEvent) {
        when (event) {
            is AuthEvent.Login -> login(event.email, event.password)
            is AuthEvent.Register -> register(event.user, event.password)
            AuthEvent.Logout -> logout()
            AuthEvent.NavigateToRegister -> _showLoginForm.value = false
            AuthEvent.NavigateToLogin -> _showLoginForm.value = true
        }
    }

    private fun login(email: String, password: String) {
        viewModelScope.launch {
            _state.value = AuthState.Loading
            val result = repository.login(email, password)
            _state.value = result.fold(
                onSuccess = { AuthState.Success(User(email = email)) },
                onFailure = { AuthState.Error(it.message ?: "Login failed") }
            )
        }
    }

    private fun register(user: User, password: String) {
        viewModelScope.launch {
            _state.value = AuthState.Loading
            val result = repository.register(user, password)
            _state.value = result.fold(
                onSuccess = { AuthState.Success(user) },
                onFailure = { AuthState.Error(it.message ?: "Registration failed") }
            )
        }
    }

    private fun logout() {
        repository.logout()
        _state.value = AuthState.Idle
    }
}