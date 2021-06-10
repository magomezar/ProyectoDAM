package com.example.proyecto.db.dao

import androidx.room.*
import com.example.proyecto.db.entities.Deseo

@Dao
interface DeseoDao {

    @Insert
    suspend fun insert(deseo: Deseo):Long

    @Update
    suspend fun update(deseo: Deseo)

    @Delete
    suspend fun delete(deseo: Deseo)

    @Query("SELECT * FROM deseos")
    suspend fun mostrarTodo(): List<Deseo>


}