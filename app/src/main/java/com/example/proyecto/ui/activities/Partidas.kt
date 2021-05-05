package com.example.proyecto.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto.R
import com.example.proyecto.adapters.RecyclerViewAdapterPartida
import com.example.proyecto.databinding.ActivityPartidasBinding
import com.example.proyecto.viewModel.JuegoViewModel


class Partidas : AppCompatActivity() {

    private lateinit var binding: ActivityPartidasBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPartidasBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val mPartida = ViewModelProvider(this).get(JuegoViewModel::class.java)

        //Boton vuelta a pantalla principal
        binding.partidasBack.setOnClickListener {
            val intent= Intent(this, MainActivity::class.java)
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

        binding.partidasBuscar.setOnClickListener {

            val jugadores = binding.partidasNumeroIn.text.toString()
            val tiempo = binding.partidasTiempoIn.text.toString()

            //Aviso campos vacios
            if (jugadores.isBlank() || tiempo.isBlank()) {
                val toast = Toast.makeText(this, "¡Todos los campos deben estar cubiertos!", Toast.LENGTH_LONG)
                toast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 600)
                toast.show()
                return@setOnClickListener
            }
            else{
                mPartida.buscarPartida(jugadores,tiempo).observe(this,{
                    val nAdapter=RecyclerViewAdapterPartida(it)
                    val recyclerView=binding.partidasRecyclerview
                    recyclerView.apply {
                        //Indicamos la orientacion de la vista del recyclerView
                        layoutManager = LinearLayoutManager(this@Partidas, RecyclerView.VERTICAL, false)
                        adapter = nAdapter
                    }
                })

            }
        }

    }
}