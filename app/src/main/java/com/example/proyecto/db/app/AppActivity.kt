package com.example.proyecto.db.app

import android.app.Application
import com.example.proyecto.db.AppDataBase

class AppActivity: Application() {

    override fun onCreate() {
        super.onCreate()
        //Inicializa la Base de Datos
        db= AppDataBase.newdb(applicationContext)
    }

    companion object{
        private var db: AppDataBase?=null
        //Se usa la función para llamar a la Base de datos
        fun getDatabase():AppDataBase?{
            return db
        }
    }
}