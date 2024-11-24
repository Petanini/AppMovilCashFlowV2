package com.example.cashflowandroidapp.ui.registerMov

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cashflowandroidapp.data.model.entities.Categoria
import com.example.cashflowandroidapp.data.model.entities.Departamento
import com.example.cashflowandroidapp.data.model.entities.Movement
import com.example.cashflowandroidapp.data.model.entities.MovementInsert
import com.example.cashflowandroidapp.data.model.entities.Naturaleza
import com.example.cashflowandroidapp.data.model.providers.MovementProvider
import kotlinx.coroutines.launch

class RegisterMovViewModel : ViewModel() {

    private val regMovProvider = MovementProvider()
    val listOfNaturaleza = MutableLiveData<List<Naturaleza>?>()
    val listOfDepartamento = MutableLiveData<List<Departamento>?>()
    val listOfCategoria = MutableLiveData<List<Categoria>?>()
    val movementRegistered = MutableLiveData<Movement>()

    fun fetchNaturaleza(){
        viewModelScope.launch {
            val result = regMovProvider.getNaturaleza()
            listOfNaturaleza.postValue(result)
        }
    }

    fun fetchDept(){
        viewModelScope.launch {
            val result = regMovProvider.getDepartamento()
            listOfDepartamento.postValue(result)
        }
    }

    fun fetchCat(){
        viewModelScope.launch {
            val result = regMovProvider.getCategoria()
            listOfCategoria.postValue(result)
        }
    }

    fun addMovement(movement: MovementInsert){
        viewModelScope.launch {
            val result = regMovProvider.createMovement(movement)
            movementRegistered.postValue(result)
        }
    }

    private val _text = MutableLiveData<String>().apply {
        value = "This is notifications Fragment"
    }
    val text: LiveData<String> = _text
}