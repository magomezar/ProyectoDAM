package com.example.proyecto.ui.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto.adapters.RecyclerViewAdapterJuegosPorEditorial
import com.example.proyecto.adapters.juegosPorEditorialListener
import com.example.proyecto.databinding.ActivityEditorialesBinding
import com.example.proyecto.db.projections.JuegosPorEditorial
import com.example.proyecto.viewModel.EditorialViewModel
import com.google.android.material.snackbar.Snackbar

class Editoriales : AppCompatActivity(), juegosPorEditorialListener {

    private lateinit var binding: ActivityEditorialesBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEditorialesBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val mEditorial = ViewModelProvider(this).get(EditorialViewModel::class.java)
        val nombreFicha = intent.extras?.get("nombre")


        binding.editorialesNombreIn.setText(nombreFicha.toString())

        binding.editorialesBack.setOnClickListener {
            val intent= Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        binding.editorialesBuscar.setOnClickListener{

            val ocultarTeclado: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            ocultarTeclado.hideSoftInputFromWindow(view.windowToken, 0)

            val editorial = binding.editorialesNombreIn.text.toString()

            //Aviso campos vacios
            if (editorial.isBlank()) {
                Snackbar.make(view, "¡Todos los campos deben estar cubiertos!", Snackbar.LENGTH_LONG).show()
                return@setOnClickListener
            }
            else {
                mEditorial.buscarEditorial(editorial).observe(this, { idEd ->
                    if (idEd == -1L) {
                        Snackbar.make(view, "La editorial no existe", Snackbar.LENGTH_LONG).show()
                        return@observe
                    }
                })
                mEditorial.buscarEditorial(editorial).observe(this,{ idAutor ->
                    mEditorial.buscarJuegos(idAutor).observe(this,{
                        val nAdapter= RecyclerViewAdapterJuegosPorEditorial(it,this)
                        val recyclerView=binding.editorialJuegosRecyclerview
                        recyclerView.apply {
                            //Indicamos la orientacion de la vista del recyclerView
                            layoutManager = LinearLayoutManager(this@Editoriales, RecyclerView.VERTICAL, false)
                            adapter = nAdapter
                        }
                    })

                })
            }

        }
    }

    override fun clickJuegoEditorial(item: JuegosPorEditorial, position: Int) {
        //TODO("Not yet implemented")
        binding.editorialesInfo.setOnClickListener {
            val intent = Intent(this, Juegos::class.java)
            intent.putExtra("nombre",item.nombre)
            startActivity(intent)
        }
        binding.editorialesNotas.setOnClickListener {
            val intent = Intent(this, Notas::class.java)
            intent.putExtra("nombre",item.nombre)
            startActivity(intent)
        }
    }
}