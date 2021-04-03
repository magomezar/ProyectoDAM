package com.example.proyecto

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.example.proyecto.databinding.ActivityMainBinding
import com.example.proyecto.db.AppDataBase
import com.example.proyecto.db.entities.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setTheme(R.style.Base_MyTheme)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val dbDes = AppDataBase.newdb(this)

        val deseo1 = Deseo("alta","atrapa la caca","Devir","Cacotas")
        val deseo2 = Deseo("muy alta","Terraforming","Maldito","Frixelius")

        val autor1 = Autor("Frixelius")
        val autor2 = Autor("Bauza")

        val editorial1 = Editorial("Maldito")
        val editorial2 = Editorial("Devir")

        val nota1 = Nota("Me encata, muy bonito")
        val nota2 = Nota("Que juego mas feo")

        val juego1 = Juego("FOTICO","Terraforming","3 horas","Eurogame","Hasta 5",1,1,1)
        val juego2 = Juego("FOTAZA","Last Bastion","2 horas","Eurogame","Hasta 4",1,1,1)

        lifecycleScope.launch{
            withContext(Dispatchers.IO){
                dbDes.deseoDao().insert(deseo1)
                dbDes.deseoDao().insert(deseo2)
                dbDes.autorDao().insert(autor1)
                dbDes.autorDao().insert(autor2)
                dbDes.editorialDao().insert(editorial1)
                dbDes.editorialDao().insert(editorial2)
                dbDes.notaDao().insert(nota1)
                dbDes.notaDao().insert(nota2)
                dbDes.juegoDao().insert(juego1)
                dbDes.juegoDao().insert(juego2)

                val lista = mutableListOf(dbDes.editorialDao().mostrarJuegosPorEditorial(1))
                lista.forEach {
                    println(it)
                }
/*                val lista2 = mutableListOf(dbDes.notaDao().mostrarNotasPorJuego(1))
                lista2.forEach {
                    println(it)
                }*/
                val lista3 = mutableListOf(dbDes.autorDao().mostrarJuegosPorAutor(1))
                lista3.forEach {
                    println(it)
                }

            }
        }

        binding.mainGuardarJuegos.setOnClickListener{
            val intent= Intent(this,Juegos::class.java)
            startActivity(intent)
        }

        binding.mainDeseos.setOnClickListener {
            val intent= Intent(this,Deseos::class.java)
            startActivity(intent)
        }

        binding.mainPartidas.setOnClickListener {
            val intent= Intent(this,Partidas::class.java)
            startActivity(intent)

        }


    }
}