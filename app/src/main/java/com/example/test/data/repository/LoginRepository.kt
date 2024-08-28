package com.example.test.data.repository
import com.example.test.data.model.LoginResponse

class LoginRepository {
    fun login(username: String, password: String, verKey: String, verCode: String, grantType: String): LoginResponse {
        // 模拟登录逻辑，这里你可以替换成API请求或数据库验证
        return if (username == "admin" && password == "password") {
            LoginResponse(success = true)
        } else {
            LoginResponse(success = false, errorMessage = "Invalid username or password")
        }
    }
}