package com.example.test.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.test.data.model.ApiResponse
import com.example.test.data.model.RepairListResponseData
import com.example.test.data.repository.RepairListRepository
import com.example.test.utils.Result
import kotlinx.coroutines.launch
import javax.inject.Inject

class RepairListViewModel @Inject constructor(private val repository: RepairListRepository): ViewModel() {
    private val _repair = MutableLiveData<Result<RepairListResponseData>>()
    val repair: LiveData<Result<RepairListResponseData>> = _repair

    fun getRepairPageBy(page: Number, rows: Number, status: String, name: String) = viewModelScope.launch {
        val response = repository.getRepairPageBy(page, rows, status, name)
        _repair.postValue(response)
    }
}