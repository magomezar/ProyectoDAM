package com.example.proyecto.db.dao

import androidx.room.*
import com.example.proyecto.db.entities.Juego
import com.example.proyecto.db.projections.JuegoProjection
import com.example.proyecto.db.projections.JuegosPartida

@Dao
interface JuegoDao {

    @Insert
    suspend fun insert(juego: Juego): Long

    @Update
    suspend fun update(juego: Juego)

    @Delete
    suspend fun delete(juego: Juego)

    @Query("SELECT * FROM juegos ORDER BY nombre ASC")
    suspend fun mostrarTodo(): List<Juego>

    @Query("SELECT * FROM juegos WHERE id=:id")
    suspend fun mostrarPorId(id: Long): Juego


    @Query("SELECT * FROM juegos WHERE nombre=:nombre")
    suspend fun buscarJuego(nombre: String): Juego?

    @Query("SELECT id FROM juegos WHERE nombre=:nombre")
    suspend fun buscarId(nombre: String): Long?

    @Query("SELECT nombre FROM juegos WHERE jugadores=:jugadores AND duracion=:tiempo")
    suspend fun buscarPartida(jugadores: String, tiempo: String): List<JuegosPartida>

    @Transaction
    @Query("SELECT juegos.id, juegos.portada, juegos.nombre, " +
            "autores.nombre_autor, editoriales.nombre_editorial "+
            "FROM juegos "+ "INNER JOIN autores ON juegos.id_autor = autores.id "+
            "INNER JOIN editoriales ON juegos.id_editorial= editoriales.id "
            + "ORDER BY juegos.nombre ASC")
    suspend fun mostrarProjectionJuego():List<JuegoProjection>

    @Query("UPDATE juegos SET portada=:portadaset WHERE id=:id")
    suspend fun actualizarPortada(portadaset:String,id:Long)

    @Query("UPDATE juegos SET nombre=:nombre, duracion=:duracion, categoria=:categoria, jugadores=:jugadores, id_autor=:idAu, id_editorial=:idEd WHERE id=:id")
    suspend fun actualizarJuego(nombre:String,duracion:String,categoria:String,jugadores:String,idAu:Long,idEd:Long,id:Long)
}