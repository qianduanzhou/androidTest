package com.example.test.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.test.data.model.LoginResponse
import com.example.test.data.repository.LoginRepository

class LoginViewModel : ViewModel() {

    private val loginRepository = LoginRepository()

    private val _loginResponse = MutableLiveData<LoginResponse>()
    val loginResponse: LiveData<LoginResponse> get() = _loginResponse

    fun login(username: String, password: String, verKey: String, verCode: String, grantType: String) {
        // 调用仓库层进行登录逻辑处理
        _loginResponse.value = loginRepository.login(username, password, verKey, verCode, grantType)
    }
}
