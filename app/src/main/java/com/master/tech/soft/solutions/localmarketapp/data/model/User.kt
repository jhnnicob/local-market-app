package com.master.tech.soft.solutions.localmarketapp.data.model

data class User(
    val id: String,
    val name: String,
    val email: String,
    val profileImageUrl: String? = null,
    val location: String? = null,
    val address: String? = null,
    val isVerified: Boolean = false
)