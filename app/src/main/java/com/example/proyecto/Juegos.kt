package com.example.proyecto

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu
import com.example.proyecto.databinding.ActivityJuegosBinding

class Juegos : AppCompatActivity() {

    private lateinit var binding: ActivityJuegosBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityJuegosBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

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

        //Aviso de campos vacios
        binding.juegosGuardar.setOnClickListener {

            val nombreJuego = binding.juegosNombreIn
            val nombre = nombreJuego.text
            val autorJuego = binding.juegosAutorIn
            val autor = autorJuego.text
            val editorialJuego = binding.juegosEditorialIn
            val editorial = editorialJuego.text
            val jugadoresJuego = binding.juegosJugadoresIn
            val jugadores = jugadoresJuego.text
            val duracionJuego = binding.juegosDuracionIn
            val duracion = duracionJuego.text
            val categoriaJuego = binding.juegosCategoriasIn
            val categoria = categoriaJuego.text

            if(nombre.isNullOrBlank()||autor.isNullOrBlank()||editorial.isNullOrBlank()||jugadores.isNullOrBlank()||duracion.isNullOrBlank()||categoria.isNullOrBlank()){
                val toast= Toast.makeText(this, "¡Todos los campos deben estar cubiertos!", Toast.LENGTH_LONG)
                toast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 600)
                toast.show()
                //binding.juegosEditorial.error ="Todos los campos deben estar cubiertos"
                return@setOnClickListener
            }

/*            val editorialJuego = binding.juegosEditorialIn
            val editorial = editorialJuego.text
            if(editorial.isNullOrBlank()){
                binding.juegosEditorial.error ="La editorial no puede estar vacía"
                return@setOnClickListener
            }*/
        }

    }
}