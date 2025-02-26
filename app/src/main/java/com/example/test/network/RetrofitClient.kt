package com.example.test.network

import com.example.test.data.api.ApiService
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "http://southsmart.com/wushui/sgeocserver/"
    private var commonService: ApiService? = null

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())  // 添加协程支持
        .build()

    fun <T> createService(service: Class<T>): T {
        return retrofit.create(service)
    }

    // 获取单例公用接口服务
    fun getCommonService(): ApiService {
        if (commonService === null) {
            commonService = createService(ApiService::class.java)
        }
        return commonService as ApiService
    }
}
