package com.example.proyecto.db.dao

import androidx.room.*
import com.example.proyecto.db.entities.Autor
import com.example.proyecto.db.projections.JuegosPorAutor

@Dao
interface AutorDao {

    @Insert
    suspend fun insert(autor: Autor): Long

    @Update
    suspend fun update(autor: Autor)

    @Delete
    suspend fun delete(autor: Autor)

    @Query("SELECT * FROM autores ORDER BY nombre_autor ASC")
    suspend fun mostrarTodo(): List<Autor>

    @Query("SELECT * FROM autores WHERE id=:id")
    suspend fun mostrarPorId(id:Long):Autor

    @Query("SELECT * FROM autores WHERE nombre_autor=:nombre")
    suspend fun buscarAutor(nombre:String):Autor?

    @Transaction
    @Query("SELECT juegos.nombre " + "FROM juegos " + "INNER JOIN autores ON juegos.id_autor = autores.id " + "WHERE autores.id=:id_autor ")
    suspend fun mostrarJuegosPorAutor(id_autor: Long): List<JuegosPorAutor>
}