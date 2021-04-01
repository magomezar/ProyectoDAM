package com.example.proyecto

import android.app.ProgressDialog.show
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.appcompat.widget.PopupMenu
import com.example.proyecto.databinding.ActivityJuegosBinding
import com.example.proyecto.databinding.ActivityMainBinding

class juegos : AppCompatActivity() {

    private lateinit var binding: ActivityJuegosBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityJuegosBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

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


    }
}