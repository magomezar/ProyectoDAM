package com.example.proyecto

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.appcompat.widget.PopupMenu
import androidx.lifecycle.lifecycleScope
import com.example.proyecto.databinding.ActivityDeseosBinding
import com.example.proyecto.db.AppDataBase
import com.example.proyecto.db.entities.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Deseos : AppCompatActivity() {

    private lateinit var binding: ActivityDeseosBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDeseosBinding.inflate(layoutInflater)
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
        val juego2 = Juego("FOTAZA","Last Bastion","2 horas","Eurogame","Hasta 4",1,2,2)

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

                val lista = mutableListOf(dbDes.autorDao().mostrarJuegosPorAutor(1))
                lista.forEach {
                    println(it)
                }

            }
        }

        //Vuelta a la pantalla principal
        binding.deseosBack.setOnClickListener {
            val intent= Intent(this,MainActivity::class.java)
            startActivity(intent)
        }

        //Desplegable de prioridades
        binding.deseosPrioridadIn.setOnClickListener {
            val prioridad = findViewById<EditText>(R.id.deseos_prioridad_in)
            PopupMenu(this, prioridad).apply {
                menuInflater.inflate(R.menu.prioridad, menu)
                setOnMenuItemClickListener { item ->
                    prioridad.setText(item.title)
                    true
                }
                show()
            }
        }




        /*val editText = findViewById<EditText>(R.id.prueba)
        editText.setOnClickListener {
            PopupMenu(this, editText).apply {
                menuInflater.inflate(R.menu.categorias, menu)
                setOnMenuItemClickListener { item ->
                    editText.setText(item.title)
                    true
                }
                show()
            }
        }*/

    }
}