package com.example.proyecto.db.dao

import androidx.room.*
import com.example.proyecto.db.entities.Nota
import com.example.proyecto.db.projections.NotasPorJuego

@Dao
interface NotaDao {

    @Insert
    suspend fun insert(nota: Nota):Long

    @Update
    suspend fun update(nota: Nota)

    @Delete
    suspend fun delete(nota: Nota)

    @Query("DELETE FROM notas WHERE id=:id")
    suspend fun borrarNotas(id: Long)

    @Query("SELECT * FROM notas")
    suspend fun mostrarTodo(): List<Nota>

    @Query("SELECT * FROM notas WHERE id_juego=:id")
    suspend fun mostrarPorJuego(id: Long): Nota

    @Transaction
    @Query("SELECT notas.nota , notas.id " + "FROM notas " + "INNER JOIN juegos ON notas.id_juego = juegos.id " + "WHERE notas.id_juego=:id ")
    suspend fun mostrarNotasPorJuego(id: Long): List<NotasPorJuego>
}