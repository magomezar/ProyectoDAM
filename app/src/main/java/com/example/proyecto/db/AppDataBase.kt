package com.example.proyecto.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.proyecto.db.dao.*
import com.example.proyecto.db.entities.*

@Database(entities = arrayOf(Deseo::class, Nota::class, Autor::class,
           Editorial::class, Juego::class),version = 1)
abstract class AppDataBase: RoomDatabase(){

    abstract fun deseoDao(): DeseoDao
    abstract fun notaDao(): NotaDao
    abstract fun autorDao(): AutorDao
    abstract fun editorialDao(): EditorialDao
    abstract fun juegoDao(): JuegoDao


    companion object{
        private var db: AppDataBase? = null
        private const val NAMEDB = "ludoteca"

        fun newdb(context: Context): AppDataBase{
            if (db==null){
                db = Room.databaseBuilder(context,
                        AppDataBase::class.java,NAMEDB).build()
            }
            return db!!
        }
    }
}