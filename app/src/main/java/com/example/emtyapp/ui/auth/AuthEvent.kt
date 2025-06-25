package com.example.emtyapp.ui.auth

import com.example.emtyapp.data.entities.User

sealed class AuthEvent {
    data class Login(val email: String, val password: String) : AuthEvent()
    data class Register(val user: User, val password: String) : AuthEvent()
    object Logout : AuthEvent()
    object NavigateToRegister : AuthEvent()
    object NavigateToLogin : AuthEvent()
}