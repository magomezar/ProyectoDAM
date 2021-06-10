package com.example.proyecto.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notas")
data class Nota(val nota: String, val id_juego: Long){

    @PrimaryKey(autoGenerate = true)
    var id: Long= 0

    override fun toString(): String {
        return "Notas (nombre='$nota')"
    }

}
