package com.example.test.data.repository

import com.example.test.data.api.ApiService
import com.example.test.data.model.ApiResponse
import com.example.test.data.model.RepairListResponseData
import com.example.test.utils.safeApiCall
import com.example.test.utils.Result

class RepairListRepository(private val apiService: ApiService) {
    suspend fun getRepairPageBy(page: Number, rows: Number, status: String, name: String): Result<RepairListResponseData> {
        return safeApiCall {
            apiService.getRepairPageBy(page, rows, status, name)
        }
    }
}