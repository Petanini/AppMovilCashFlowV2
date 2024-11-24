package com.example.cashflowandroidapp.data.interfaces

import com.example.cashflowandroidapp.data.model.entities.Categoria
import com.example.cashflowandroidapp.data.model.entities.Departamento
import com.example.cashflowandroidapp.data.model.entities.Movement
import com.example.cashflowandroidapp.data.model.entities.MovementInsert
import com.example.cashflowandroidapp.data.model.entities.Naturaleza
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface IMovement {
    @GET("getMovimientos")
    suspend fun getMovimientosAll(): List<Movement>

    /*@GET("")
    suspend fun  getMovimientoBanco(@Query("id_banco") id_banco: Int): List<Movement>*/

    @GET("movimientos-bancarios/departamento")
    suspend fun getDepts(): List<Departamento>
    @GET("movimientos-bancarios/naturaleza")
    suspend fun getNaturaleza(): List<Naturaleza>
    @GET("movimientos-bancarios/categoria")
    suspend fun getCategoria(): List<Categoria>

    @POST("movimientos-bancarios/saveMovimientos")
    suspend fun saveMovimiento(@Body obj: MovementInsert): Movement
}