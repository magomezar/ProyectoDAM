package com.example.proyecto.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextThemeWrapper
import android.widget.EditText
import androidx.appcompat.widget.PopupMenu
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto.R
import com.example.proyecto.adapters.RecyclerViewAdapterPartida
import com.example.proyecto.databinding.ActivityPartidasBinding
import com.example.proyecto.viewModel.JuegoViewModel
import com.google.android.material.snackbar.Snackbar


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
            val numero = findViewById<EditText>(R.id.partidas_numero_in)
            var formato = ContextThemeWrapper(this, R.style.PopupMenu)
            PopupMenu(formato, numero).apply {
                menuInflater.inflate(R.menu.jugadores, menu)
                setOnMenuItemClickListener { item ->
                    numero.setText(item.title)
                    true
                }
                show()
            }
        }

        //Desplegable tiempo de partida
        binding.partidasTiempoIn.setOnClickListener {
            val duracion = findViewById<EditText>(R.id.partidas_tiempo_in)
            var formato = ContextThemeWrapper(this, R.style.PopupMenu)
            PopupMenu(formato, duracion).apply {
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
                Snackbar.make(view, "¡Todos los campos deben estar cubiertos!", Snackbar.LENGTH_LONG).show()
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