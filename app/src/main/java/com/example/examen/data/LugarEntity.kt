package com.example.examen.data

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "lugares")
data class LugarEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val nombre: String,
    val ordenVisita: Int,
    val imageUrl: String,
    val latitud: Double,
    val longitud: Double,
    val costoAlojamiento: Double,
    val costoTransporte: Double,
    val comentarios: String


)
