package com.example.cashflowandroidapp.data.utils

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitConnection {

    private const val URL = "https://idatprueba.accura.pe/api/"

    val apiRetrofitService = Retrofit.Builder()
        .baseUrl(URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}