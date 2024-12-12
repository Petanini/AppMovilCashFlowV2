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
    val listOfCategoria = MutableLiveData<List<Categoria>?>()
    val movementRegistered = MutableLiveData<Movement>()
    val errorMessage = MutableLiveData<String?>()



    fun fetchCat() {
        viewModelScope.launch {
            try {
                val result = regMovProvider.getCategoria()
                listOfCategoria.postValue(result)
            } catch (e: Exception) {
                errorMessage.postValue("Error al cargar categorías: ${e.message}")
            }
        }
    }

    fun addMovement(movement: MovementInsert) {
        viewModelScope.launch {
            try {
                val result = regMovProvider.createMovement(movement)
                movementRegistered.postValue(result)
            } catch (e: Exception) {
                errorMessage.postValue("Error al registrar movimiento: ${e.message}")
            }
        }
    }

    private val _text = MutableLiveData<String>().apply {
        value = "This is notifications Fragment"
    }
    val text: LiveData<String> = _text
}