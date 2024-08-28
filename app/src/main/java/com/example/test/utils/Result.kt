package com.example.test.utils
import com.example.test.data.model.ApiResponse

/**
 * 统一处理接口返回
 */
sealed class Result<out T> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val message: String) : Result<Nothing>()
}


/**
 * 通用网络请求处理
 */
suspend fun <T> safeApiCall(apiCall: suspend () -> ApiResponse<T>): Result<T> {
    return try {
        val response = apiCall.invoke()
        if (response.success) {
            Result.Success(response.data)
        } else {
            Result.Error(response.message ?: "请求失败")
        }
    } catch (e: Exception) {
        Result.Error(e.message ?: "请求失败")
    }
}