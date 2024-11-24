package com.example.cashflowandroidapp.ui.transactions

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cashflowandroidapp.data.model.entities.Movement
import com.example.cashflowandroidapp.data.model.providers.MovementProvider
import kotlinx.coroutines.launch

class TransactionsViewModel : ViewModel() {

    private val movProvider = MovementProvider()
    val listOfMovements = MutableLiveData<List<Movement>>()

    fun fetchMovements(){
        viewModelScope.launch {
            val movements = movProvider.getMovements()
            listOfMovements.postValue(movements)
        }
    }

    private val _text = MutableLiveData<String>().apply {
        value = "This is dashboard Fragment"
    }
    val text: LiveData<String> = _text
}