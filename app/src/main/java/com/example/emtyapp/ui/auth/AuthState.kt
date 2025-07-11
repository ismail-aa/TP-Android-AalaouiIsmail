package com.example.emtyapp.ui.auth

import com.example.emtyapp.data.entities.User

sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    data class Success(val user: User) : AuthState()
    data class Error(val message: String) : AuthState()
}