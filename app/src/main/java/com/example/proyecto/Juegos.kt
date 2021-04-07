package com.example.proyecto

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu
import androidx.lifecycle.lifecycleScope
import com.example.proyecto.databinding.ActivityJuegosBinding
import com.example.proyecto.db.AppDataBase
import com.example.proyecto.db.entities.Autor
import com.example.proyecto.db.entities.Editorial
import com.example.proyecto.db.entities.Juego
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Juegos : AppCompatActivity() {

    private val TAG="MAIN_ACTIVITY"
    private lateinit var binding: ActivityJuegosBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityJuegosBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val dbDes = AppDataBase.newdb(this)

        //Boton vuelta a pantalla principal
        binding.juegosBack.setOnClickListener {
            val intent= Intent(this,MainActivity::class.java)
            startActivity(intent)
        }

        //Desplegable de categorias
        binding.juegosCategoriasIn.setOnClickListener {
            val categorias = findViewById<EditText>(R.id.juegos_categorias_in)
            PopupMenu(this, categorias).apply {
                menuInflater.inflate(R.menu.categorias, menu)
                setOnMenuItemClickListener { item ->
                    categorias.setText(item.title)
                    true
                }
                show()
            }
        }

        //Desplegable para la duracion
        binding.juegosDuracionIn.setOnClickListener {
        val duracion = findViewById<EditText>(R.id.juegos_duracion_in)
            PopupMenu(this, duracion).apply {
                menuInflater.inflate(R.menu.duracion, menu)
                setOnMenuItemClickListener { item ->
                    duracion.setText(item.title)
                    true
                }
                show()
            }
        }

        //Desplegable para el numero de jugadores
        binding.juegosJugadoresIn.setOnClickListener {
        val jugadores = findViewById<EditText>(R.id.juegos_jugadores_in)
            PopupMenu(this, jugadores).apply {
                menuInflater.inflate(R.menu.jugadores, menu)
                setOnMenuItemClickListener { item ->
                    jugadores.setText(item.title)
                    true
                }
                show()
            }
        }

        //Guardar juego
        binding.juegosGuardar.setOnClickListener {

            //val portadaJuego = binding.juegosImagen
            //val portada = portadaJuego
            val nombre = binding.juegosNombreIn.text.toString()
            val autor = binding.juegosAutorIn.text.toString()
            val editorial = binding.juegosEditorialIn.text.toString()
            val jugadores = binding.juegosJugadoresIn.text.toString()
            val duracion = binding.juegosDuracionIn.text.toString()
            val categoria = binding.juegosCategoriasIn.text.toString()

            //Aviso campos vacios
            if(nombre.isBlank()||autor.isBlank()||editorial.isBlank()||jugadores.isBlank()||duracion.isBlank()||categoria.isBlank()){
                val toast= Toast.makeText(this, "¡Todos los campos deben estar cubiertos!", Toast.LENGTH_LONG)
                toast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 600)
                toast.show()
                return@setOnClickListener
            }

            else {

                lifecycleScope.launch {
                    withContext(Dispatchers.IO) {
                        val idAutor: Long
                        val idEditorial: Long

                        //Busca si existe autor, si no inserta
                        if(dbDes.autorDao().buscarAutor(autor) != autor){
                            dbDes.autorDao().insert(Autor(autor))
                            //Busca y devuelve id insertado
                            idAutor= dbDes.autorDao().buscarIdAutor(autor)
                        }else{
                            idAutor= dbDes.autorDao().buscarIdAutor(autor)
                            Log.d(TAG, idAutor.toString())
                       }

                        //Busca si existe editorial, si no inserta
                        if(dbDes.editorialDao().buscarEditorial(editorial) != editorial){
                            dbDes.editorialDao().insert(Editorial(editorial))
                            //Busca y devuelve id insertado
                            idEditorial= dbDes.editorialDao().buscarIdEditorial(editorial)
                        }else{
                            idEditorial= dbDes.editorialDao().buscarIdEditorial(editorial)
                            Log.d(TAG, idEditorial.toString())
                        }
                        dbDes.juegoDao().insert(
                            Juego(
                                "portada.toString()",
                                nombre, duracion, categoria,
                                jugadores,idAutor, idEditorial)
                        )
                    }
                }
            }

        }

    }
}