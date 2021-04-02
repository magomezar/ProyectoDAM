package com.example.proyecto

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.widget.PopupMenu
import com.example.proyecto.databinding.ActivityPartidasBinding

class partidas : AppCompatActivity() {

    private lateinit var binding: ActivityPartidasBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPartidasBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        //Boton vuelta a pantalla principal
        binding.partidasBack.setOnClickListener {
            val intent= Intent(this,MainActivity::class.java)
            startActivity(intent)
        }

        //Desplegable numero de jugadores
        binding.partidasNumeroIn.setOnClickListener {
            val duracion = findViewById<EditText>(R.id.partidas_numero_in)
            PopupMenu(this, duracion).apply {
                menuInflater.inflate(R.menu.jugadores, menu)
                setOnMenuItemClickListener { item ->
                    duracion.setText(item.title)
                    true
                }
                show()
            }
        }

        //Desplegable tiempo de partida
        binding.partidasTiempoIn.setOnClickListener {
            val duracion = findViewById<EditText>(R.id.partidas_tiempo_in)
            PopupMenu(this, duracion).apply {
                menuInflater.inflate(R.menu.duracion, menu)
                setOnMenuItemClickListener { item ->
                    duracion.setText(item.title)
                    true
                }
                show()
            }
        }

    }
}