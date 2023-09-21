package com.example.examen.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.examen.Lugar


@Dao
interface LugarDao {
    @Query("SELECT * FROM lugares ORDER BY ordenVisita ASC")
    suspend fun getLugaresOrdenados(): LiveData<List<Lugar>>

    @Insert
    suspend fun insertLugar(lugar: Lugar)

    @Delete
    suspend fun deleteLugar(lugar: Lugar)
}