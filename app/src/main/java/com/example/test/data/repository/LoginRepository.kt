package com.example.test.data.repository
import com.example.test.data.api.ApiService
import com.example.test.data.model.LoginResponseData
import com.example.test.utils.Result
import com.example.test.utils.safeApiCall

class LoginRepository(private val apiService: ApiService) {
    suspend fun login(username: String, password: String, verKey: String, verCode: String, grantType: String): Result<LoginResponseData> {
        return safeApiCall {
            apiService.login(username, password, verKey, verCode, grantType);
        }
    }
}