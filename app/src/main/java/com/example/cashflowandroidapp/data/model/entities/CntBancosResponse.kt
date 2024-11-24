package com.example.cashflowandroidapp.data.model.entities

data class CntBancosResponse(
    val id_egre_banc: Int,
    val id_empresa: Int,
    val egre_banc_opc: String,
    val egre_banc_tipo_mone: Int,
    val egre_banc_num_cuen: String,
    val egre_banc_nomb_alte: String,
    val egre_banc_img: String,
    val cuenta_monto: String
)
