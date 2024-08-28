package com.example.test.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.test.data.model.ApiResponse
import com.example.test.data.model.CodeResponseData
import com.example.test.data.repository.CodeRepository
import com.example.test.utils.Result
import kotlinx.coroutines.launch
import javax.inject.Inject

class CodeViewModel @Inject constructor(private val repository: CodeRepository) : ViewModel() {
    private val _code = MutableLiveData<Result<CodeResponseData>>()
    val code: LiveData<Result<CodeResponseData>> = _code

    fun fetchCode() = viewModelScope.launch {
        val response = repository.getCode()
        _code.postValue(response)
    }
}