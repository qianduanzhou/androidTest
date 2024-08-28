package com.example.test.repository
import com.example.test.model.LoginResult

class AuthRepository {
    fun login(username: String, password: String): LoginResult {
        // 模拟登录逻辑，这里你可以替换成API请求或数据库验证
        return if (username == "admin" && password == "password") {
            LoginResult(success = true)
        } else {
            LoginResult(success = false, errorMessage = "Invalid username or password")
        }
    }
}