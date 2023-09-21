package com.example.examen.data

import androidx.lifecycle.LiveData
import com.example.examen.Lugar
import com.example.examen.data.LugarDao


class LugarRepository(private val lugarDao: LugarDao) {

    val readAllData: LiveData<List<Lugar>> = lugarDao.getLugaresOrdenados()

    suspend fun addLugar(lugar: Lugar){
        lugarDao.addLugar(lugar)
    }
}