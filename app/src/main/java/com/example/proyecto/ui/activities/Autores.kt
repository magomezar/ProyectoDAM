package com.example.proyecto.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto.adapters.RecyclerViewAdapterJuegosPorAutor
import com.example.proyecto.adapters.juegosPorAutorListener
import com.example.proyecto.databinding.ActivityAutoresBinding
import com.example.proyecto.db.projections.JuegosPorAutor
import com.example.proyecto.viewModel.AutorViewModel

class Autores : AppCompatActivity(), juegosPorAutorListener{

    private lateinit var binding: ActivityAutoresBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAutoresBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val nombreFicha = intent.extras?.get("nombre")
        val mAutor = ViewModelProvider(this).get(AutorViewModel::class.java)

        binding.autoresNombreIn.setText(nombreFicha.toString())

        binding.autoresBack.setOnClickListener {
            val intent= Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        binding.autoresBuscar.setOnClickListener{

            val autor = binding.autoresNombreIn.text.toString()

            //Aviso campos vacios
            if (autor.isBlank()) {
                val toast = Toast.makeText(this, "¡Todos los campos deben estar cubiertos!", Toast.LENGTH_LONG)
                toast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 600)
                toast.show()
                return@setOnClickListener
            }
            else{
                mAutor.buscarAutor(autor).observe(this,{ idAutor ->
                    mAutor.buscarJuegos(idAutor).observe(this,{
                        val nAdapter= RecyclerViewAdapterJuegosPorAutor(it,this)
                        val recyclerView=binding.autorJuegosRecyclerview
                        recyclerView.apply {
                            //Indicamos la orientacion de la vista del recyclerView
                            layoutManager = LinearLayoutManager(this@Autores, RecyclerView.VERTICAL, false)
                            adapter = nAdapter
                        }
                    })

                })
            }

        }

    }

    override fun clickJuegoAutor(item: JuegosPorAutor, position: Int) {
        binding.autoresInfo.setOnClickListener {
            val intent = Intent(this, Juegos::class.java)
            intent.putExtra("nombre",item.nombre)
            startActivity(intent)
        }
        binding.autoresNotas.setOnClickListener {
            val intent = Intent(this, Notas::class.java)
            intent.putExtra("nombre",item.nombre)
            startActivity(intent)
        }
    }
}