package com.example.cashflowandroidapp.ui.shared

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel : ViewModel() {
    val idBanco = MutableLiveData<Int>(0)

    fun setIdBanco(id: Int) {
        idBanco.value = id
    }
}