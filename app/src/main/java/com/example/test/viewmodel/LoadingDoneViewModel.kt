package com.example.test.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

// 是否加载到底
class LoadingDoneViewModel : ViewModel()  {
    val isLoadingDone = MutableLiveData<Boolean>(false)
    // 获取
    fun getLoadingDone(): Boolean {
        return isLoadingDone.value ?: false
    }
    // 展示
    fun show() {
        isLoadingDone.value = true;
    }
    // 隐藏
    fun hide() {
        isLoadingDone.value = false;
    }
}