package com.example.examen.data

import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response

data class IndicadorData(
    val codigo: String,
    val nombre: String,
    val unidad_medida: String,
    val valor: Double,
    val fecha: String
)

fun main() {
    val url = "https://mindicador.cl/api"

    val client = OkHttpClient()
    val request = Request.Builder()
        .url(url)
        .build()

    try {
        val response: Response = client.newCall(request).execute()
        if (response.isSuccessful) {
            val responseBody = response.body?.string()


            val gson = Gson()
            val indicadorData = gson.fromJson(responseBody, IndicadorData::class.java)

            println("Nombre: ${indicadorData.nombre}")
            println("Valor: ${indicadorData.valor}")
            println("Fecha: ${indicadorData.fecha}")
        } else {
            println("Error en la solicitud: ${response.code}")
        }
    } catch (e: Exception) {
        println("Error: ${e.message}")
    }
}

