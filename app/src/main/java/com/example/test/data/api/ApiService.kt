package com.example.test.data.api

import com.example.test.data.model.ApiResponse
import com.example.test.data.model.CodeResponseData
import retrofit2.http.GET


interface ApiService {
    // 获取验证码
    @GET("system/login/captcha")
    suspend fun getCode(): ApiResponse<CodeResponseData>
}