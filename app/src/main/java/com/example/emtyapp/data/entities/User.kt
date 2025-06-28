package com.example.emtyapp.data.entities

data class User(
    val uid: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val username: String = "",
    val email: String = "",
    val birthDate: String = "",
    var role: String = "client", // client, gerant, admin
    val address: String = ""
)