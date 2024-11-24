package com.example.cashflowandroidapp.data.model.providers

import com.example.cashflowandroidapp.data.interfaces.IEmpresas
import com.example.cashflowandroidapp.data.model.entities.CntBancosResponse
import com.example.cashflowandroidapp.data.utils.RetrofitConnection
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import retrofit2.Response

class CntBancosProvider {

    private val iEmpresas = RetrofitConnection.apiRetrofitService.create(IEmpresas::class.java)

    suspend fun getCntsBancosResponse(id_empresa: Int): List<CntBancosResponse> {
        var data: List<CntBancosResponse>
        runBlocking (Dispatchers.IO) {
            data = iEmpresas.getCuentasBancos(id_empresa)
        }
        return data
    }
}