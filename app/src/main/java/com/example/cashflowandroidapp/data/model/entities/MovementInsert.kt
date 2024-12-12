package com.example.cashflowandroidapp.data.model.entities

data class MovementInsert(
    val concepto: String,
    val id_banco: Int,
    val fecha: String,
    val monto: Double,
    val tipo: Int,
    val categoria: Int
)
