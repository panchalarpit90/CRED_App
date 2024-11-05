package com.example.cred.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cred.model.Properties
import com.example.cred.network.ProductApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

enum class StatusApi { LOADING, ERROR, LOADED }

class HomeFragmentViewModel : ViewModel() {
    private val _status = MutableLiveData<StatusApi>()
    val status: LiveData<StatusApi>
        get() = _status

    private val _properties = MutableLiveData<Properties>()
    val properties: LiveData<Properties>
        get() = _properties

    init {
        getProducts()
    }

    private fun getProducts() {
        viewModelScope.launch(Dispatchers.Main) {
            try {
                _status.value = StatusApi.LOADING
                val listResult = ProductApi.retrofitService.getProducts()
                _properties.value = listResult
                _status.value = StatusApi.LOADED
            } catch (e: Exception) {
                _status.value = StatusApi.ERROR
            }
        }
    }
}
