package com.example.test.data.model

data class ApiResponse<T>(
    val success: Boolean,
    val code: String,
    val message: String,
    val data: T
)
