package com.example.emtyapp.ui.profile

import com.example.emtyapp.data.entities.User

data class ProfileState(
    val isLoading: Boolean = false,
    val user: User? = null,
    val error: String? = null
)