package com.example.proyecto.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto.adapters.RecyclerViewAdapterJuego
import com.example.proyecto.adapters.RecyclerViewAdapterNota
import com.example.proyecto.adapters.notaListener
import com.example.proyecto.databinding.ActivityNotasBinding
import com.example.proyecto.db.entities.Nota
import com.example.proyecto.db.projections.NotasPorJuego
import com.example.proyecto.viewModel.JuegoViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.snackbar.SnackbarContentLayout
import kotlinx.coroutines.withContext

class Notas : AppCompatActivity(), notaListener {

    private lateinit var binding: ActivityNotasBinding
    private lateinit var rAdapter: RecyclerViewAdapterNota
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNotasBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val mJuego = ViewModelProvider(this).get(JuegoViewModel::class.java)
        val nombreFicha = intent.extras?.get("nombre")
        binding.notasNombreJuego.setText(nombreFicha.toString())

        ////
        mJuego.juegoCompleto(nombreFicha.toString()).observe(this, {
            mJuego.mostrarNotasPorJuego(it.id).observe(this, {
                rAdapter = RecyclerViewAdapterNota(it as MutableList<NotasPorJuego>, this)
                val recycler = binding.notasRecyclerview
                recycler.apply {
                    layoutManager = LinearLayoutManager(this@Notas, RecyclerView.VERTICAL,false)
                    adapter = rAdapter
                }
            })
        })


        //Boton vuelta a pantalla principal
        binding.notasBack.setOnClickListener {
            val intent= Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        //Agregar notas
        binding.notasAgregar.setOnClickListener {

            val nota = binding.notasFormularioIn.text.toString()

            if (nota.isBlank()) {
                Snackbar.make(view, "No se ha escrito ninguna nota", Snackbar.LENGTH_SHORT).show()

                return@setOnClickListener
            }  else {
                mJuego.juegoCompleto(nombreFicha.toString()).observe(this, {

                    mJuego.agregarNotasJuego(Nota(nota,it.id)).observe(this, {
                       rAdapter.agregarNota(it)
                    })
                })
                Snackbar.make(view, "La nota ha sido guardada", Snackbar.LENGTH_LONG).show()
            }
        }


    }

    override fun clickNota(item: NotasPorJuego, position: Int) {
        //TODO("Not yet implemented")
        binding.notasBorrar.setOnClickListener {
            val mJuego = ViewModelProvider(this).get(JuegoViewModel::class.java)
            mJuego.borrarNotas(item.id)
            rAdapter.borrarNota(position)
            //Snackbar.make(view, "Nota borrada", Snackbar.LENGTH_LONG).show()
        }
    }
}