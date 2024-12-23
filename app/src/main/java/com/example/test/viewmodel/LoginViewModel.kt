package com.example.test.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.test.data.model.ApiResponse
import com.example.test.data.model.LoginResponseData
import com.example.test.data.repository.LoginRepository
import com.example.test.utils.Result
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoginViewModel @Inject constructor(private val repository: LoginRepository): ViewModel() {
    private val _login = MutableLiveData<Result<LoginResponseData>>()
    val login: LiveData<Result<LoginResponseData>> = _login

    fun login(username: String, password: String, verKey: String, verCode: String, grantType: String) = viewModelScope.launch {
//        val response = repository.login(username, password, verKey, verCode, grantType)
//        _login.postValue(response)
        val apiResponse = ApiResponse(
            success = true,
            code = "200",
            message = "请求成功",
            data = LoginResponseData("token")
        )
        val result = Result.Success(apiResponse.data)
        _login.postValue(result)
    }
}