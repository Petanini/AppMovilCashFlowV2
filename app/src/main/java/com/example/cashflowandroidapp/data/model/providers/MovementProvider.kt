package com.example.cashflowandroidapp.data.model.providers

import com.example.cashflowandroidapp.data.interfaces.IMovement
import com.example.cashflowandroidapp.data.model.entities.Categoria
import com.example.cashflowandroidapp.data.model.entities.Departamento
import com.example.cashflowandroidapp.data.model.entities.Movement
import com.example.cashflowandroidapp.data.model.entities.MovementInsert
import com.example.cashflowandroidapp.data.model.entities.Naturaleza
import com.example.cashflowandroidapp.data.utils.RetrofitConnection
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking

class MovementProvider {

    private val imov = RetrofitConnection.apiRetrofitService.create(IMovement::class.java)

    suspend fun getMovements(): List<Movement>{

        var data: List<Movement>
        runBlocking(Dispatchers.IO){
            data = imov.getMovimientosAll()
        }
        return data
    }

    suspend fun getMovementsById(id_banco: Int): List<Movement>{

        var data: List<Movement>
        runBlocking(Dispatchers.IO){
            data = imov.getMovimientoBanco(id_banco)
        }
        return data
    }

    suspend fun getNaturaleza(): List<Naturaleza>{
        var data: List<Naturaleza>
        runBlocking(Dispatchers.IO){
            data = imov.getNaturaleza()
        }
        return data
    }

    suspend fun getCategoria(): List<Categoria>{
        var data: List<Categoria>
        runBlocking(Dispatchers.IO){
            data = imov.getCategoria()
        }
        return data
    }

    suspend fun getDepartamento(): List<Departamento>{
        var data: List<Departamento>
        runBlocking(Dispatchers.IO){
            data = imov.getDepts()
        }
        return data
    }

    suspend fun createMovement(movement: MovementInsert): Movement{
        var data: Movement
        runBlocking(Dispatchers.IO) {
            data = imov.saveMovimiento(movement)
        }
        return data
    }
}