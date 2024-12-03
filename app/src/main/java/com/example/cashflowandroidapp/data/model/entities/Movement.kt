package com.example.cashflowandroidapp.data.model.entities

data class Movement(
    val id_mov: Int,
    val concepto: String,
    val id_banco: Int,
    val fecha: String,
    val monto: Double,
    val tipo: Int,
    val bancos: CntBancosResponse
)
