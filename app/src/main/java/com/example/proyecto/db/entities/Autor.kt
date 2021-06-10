package com.example.proyecto.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "autores")
data class Autor (@ColumnInfo(name = "nombre_autor")val nombreAutor:String) {

    @PrimaryKey(autoGenerate = true)
    var id: Long= 0

    override fun toString(): String {
        return "Autor(nombre='$nombreAutor')"
    }
}