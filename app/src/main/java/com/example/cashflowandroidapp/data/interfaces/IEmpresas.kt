package com.example.cashflowandroidapp.data.interfaces

import com.example.cashflowandroidapp.data.model.entities.CntBancosResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface IEmpresas {

    @GET("cuentas-banco/{id_empresa}")
    suspend fun getCuentasBancos(@Query("id_empresa") id_empresa: Int): List<CntBancosResponse>
}