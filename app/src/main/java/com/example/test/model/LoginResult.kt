package com.example.test.model

data class LoginResult(
    val success: Boolean,
    val errorMessage: String? = null
)