package com.example.examen

data class Lugar(
    val lugar: String = "",
    val lugares: List<Lugar> = emptyList()

)
