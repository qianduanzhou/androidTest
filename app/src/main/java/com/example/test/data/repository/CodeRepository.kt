package com.example.test.data.repository

import com.example.test.data.api.ApiService
import com.example.test.data.model.ApiResponse
import com.example.test.utils.Result
import com.example.test.utils.safeApiCall
import com.example.test.data.model.CodeResponseData

// 验证码获取
class CodeRepository(private val apiService: ApiService) {
    suspend fun getCode(): Result<CodeResponseData> {

        return safeApiCall {
            apiService.getCode();
        }
    }
}
