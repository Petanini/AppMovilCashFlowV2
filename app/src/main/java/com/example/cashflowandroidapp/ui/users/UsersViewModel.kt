package com.example.cashflowandroidapp.ui.users

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cashflowandroidapp.data.model.entities.CntBancosResponse
import com.example.cashflowandroidapp.data.model.providers.CntBancosProvider
import kotlinx.coroutines.launch
import retrofit2.Response

class UsersViewModel : ViewModel() {

    private val cuentasProvider = CntBancosProvider()
    val listOfCuentas = MutableLiveData<List<CntBancosResponse>?>()


    fun fetchCuentas(id_empresa: Int) {
        viewModelScope.launch {
            val result = cuentasProvider.getCntsBancosResponse(id_empresa)
            listOfCuentas.postValue(result)
        }
    }

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text


}