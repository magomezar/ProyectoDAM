package com.example.proyecto.ui.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto.adapters.RecyclerViewAdapterJuegosPorAutor
import com.example.proyecto.adapters.juegosPorAutorListener
import com.example.proyecto.databinding.ActivityAutoresBinding
import com.example.proyecto.db.projections.JuegosPorAutor
import com.example.proyecto.viewModel.AutorViewModel
import com.google.android.material.snackbar.Snackbar

class Autores : AppCompatActivity(), juegosPorAutorListener{

    private lateinit var binding: ActivityAutoresBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAutoresBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val mAutor = ViewModelProvider(this).get(AutorViewModel::class.java)
        val nombreFicha = intent.extras?.get("nombre")


        binding.autoresNombreIn.setText(nombreFicha.toString())

        binding.autoresBack.setOnClickListener {
            val intent= Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        binding.autoresBuscar.setOnClickListener{

            val ocultarTeclado: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            ocultarTeclado.hideSoftInputFromWindow(view.windowToken, 0)

            val autor = binding.autoresNombreIn.text.toString()

            //Aviso campos vacios
            if (autor.isBlank()) {
                Snackbar.make(view, "¡Todos los campos deben estar cubiertos!", Snackbar.LENGTH_LONG).show()
                return@setOnClickListener
            }
            else {
                mAutor.buscarAutor(autor).observe(this, { idEd ->
                    if (idEd == -1L) {
                        Snackbar.make(view, "El autor no existe", Snackbar.LENGTH_LONG).show()
                        return@observe
                    }
                })
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