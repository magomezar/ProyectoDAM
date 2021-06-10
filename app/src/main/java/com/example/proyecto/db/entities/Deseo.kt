package com.example.proyecto.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "deseos")
data class Deseo(val prioridad:String,
                 @ColumnInfo(name = "nom_juego") val nomJuego:String,
                 @ColumnInfo(name = "editorial") val nomEditorial:String,
                 @ColumnInfo(name = "autor") val nomAutor:String){

    @PrimaryKey(autoGenerate = true)
    var id:Long = 0
}
