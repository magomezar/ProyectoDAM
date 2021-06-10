package com.example.proyecto.db.dao

import androidx.room.*
import com.example.proyecto.db.entities.Editorial
import com.example.proyecto.db.projections.JuegosPorEditorial

@Dao
interface EditorialDao {
    @Insert
    suspend fun insert(editorial: Editorial): Long

    @Update
    suspend fun update(editorial: Editorial)

    @Delete
    suspend fun delete(editorial: Editorial)

    @Query("SELECT * FROM editoriales ORDER BY nombre_editorial ASC")
    suspend fun mostrarTodo(): List<Editorial>

    @Query("SELECT * FROM editoriales WHERE id=:id")
    suspend fun mostrarPorId(id:Long):Editorial

    @Query("SELECT * FROM editoriales WHERE nombre_editorial=:nombre")
    suspend fun buscarEditorial(nombre:String):Editorial?


    @Transaction
    @Query("SELECT juegos.nombre " + "FROM juegos " + "INNER JOIN editoriales ON juegos.id_editorial = editoriales.id " + "WHERE editoriales.id=:id_editorial ")
    suspend fun mostrarJuegosPorEditorial(id_editorial: Long): List<JuegosPorEditorial>
}