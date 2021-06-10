package com.example.proyecto.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "editoriales")
data class Editorial(@ColumnInfo(name = "nombre_editorial")val nombreEditorial: String){

    @PrimaryKey(autoGenerate = true)
    var id: Long= 0

    override fun toString(): String {
        return "Editorial(nombre='$nombreEditorial')"
    }

}