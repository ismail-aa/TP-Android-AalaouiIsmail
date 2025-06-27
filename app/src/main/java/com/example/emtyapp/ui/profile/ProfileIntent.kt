package com.example.emtyapp.ui.profile

import com.example.emtyapp.data.entities.User

sealed class ProfileIntent {
    object LoadUserData : ProfileIntent()
    data class UpdateUser(val user: User) : ProfileIntent()
}