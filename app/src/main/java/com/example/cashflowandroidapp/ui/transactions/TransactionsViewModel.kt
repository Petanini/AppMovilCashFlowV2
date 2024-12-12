package com.example.cashflowandroidapp.ui.transactions

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.cashflowandroidapp.data.model.entities.Movement
import com.example.cashflowandroidapp.data.model.providers.MovementProvider
import kotlinx.coroutines.launch

class TransactionsViewModel(savedStateHandle: SavedStateHandle) : ViewModel() {
    private val movProvider = MovementProvider()
    val listOfMovements = MutableLiveData<List<Movement>>()
    private val _idBanco = savedStateHandle.getLiveData<Int>("id_banco", 0)
    private val _bancoMonto = savedStateHandle.getLiveData<String>("monto_banco", "0")

    val id_banco: LiveData<Int> = _idBanco
    val banco_monto: LiveData<String> = _bancoMonto

    fun setIdBanco(id: Int) {
        println("setIdBanco")
        println(id)
        _idBanco.value = id
    }

    fun setBancoMonto(monto: String){
        _bancoMonto.value = monto
    }

    fun fetchMovements() {
        val idBancoValue = _idBanco.value ?: 0
        println(id_banco)
        println("desde el fetchMovememtns")
        println(idBancoValue)
        println(_idBanco.value)
        viewModelScope.launch {
            val movements = if (idBancoValue == 0) {
                movProvider.getMovements()
            } else {
                movProvider.getMovementsById(idBancoValue)
            }
            listOfMovements.postValue(movements)
        }
    }

    private val _text = MutableLiveData<String>().apply {
        value = "This is dashboard Fragment"
    }
    val text: LiveData<String> = _text
}