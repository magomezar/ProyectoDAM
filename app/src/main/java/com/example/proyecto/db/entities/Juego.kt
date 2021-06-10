package com.example.proyecto.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "juegos")
data class Juego(val portada: String,
                 val nombre: String,
                 val duracion: String,
                 val categoria: String,
                 val jugadores: String,
                 @ColumnInfo(name="id_autor") val idAutor: Long,
                 @ColumnInfo(name="id_editorial") val idEditorial: Long){

    @PrimaryKey(autoGenerate = true)
    var id: Long= 0

}

