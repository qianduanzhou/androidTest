package com.example.test.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

// loading
class LoadingViewModel : ViewModel() {
    val isLoading = MutableLiveData<Boolean>(false)
    // 获取
    fun getLoading(): Boolean {
        return isLoading.value ?: false
    }
    // 展示
    fun showLoading() {
        isLoading.value = true
    }
    // 隐藏
    fun hideLoading() {
        isLoading.value = false
    }
}