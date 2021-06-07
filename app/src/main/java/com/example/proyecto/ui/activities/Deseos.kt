package com.example.proyecto.ui.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextThemeWrapper
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.widget.PopupMenu
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto.R
import com.example.proyecto.adapters.RecyclerViewAdapterDeseo
import com.example.proyecto.adapters.deseoListener
import com.example.proyecto.databinding.ActivityDeseosBinding
import com.example.proyecto.db.entities.*
import com.example.proyecto.viewModel.DeseoViewModel
import com.google.android.material.snackbar.Snackbar

class Deseos : AppCompatActivity(),deseoListener {

    private lateinit var binding: ActivityDeseosBinding
    private lateinit var rAdapter: RecyclerViewAdapterDeseo
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDeseosBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val mDeseo = ViewModelProvider(this).get(DeseoViewModel::class.java)
        mDeseo.mostrarDeseos().observe(this,{
            rAdapter= RecyclerViewAdapterDeseo(it as MutableList<Deseo>,this)
            val recyclerView=binding.deseosRecyclerview
            recyclerView.apply {
                //Indicamos la orientacion de la vista del recyclerView
                layoutManager = LinearLayoutManager(this@Deseos, RecyclerView.VERTICAL, false)
                adapter = rAdapter
            }
        })

        //Vuelta a la pantalla principal
        binding.deseosBack.setOnClickListener {
            val intent= Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        //Desplegable de prioridades
        binding.deseosPrioridadIn.setOnClickListener {

            val ocultarTeclado: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            ocultarTeclado.hideSoftInputFromWindow(view.windowToken, 0)

            val prioridad = findViewById<EditText>(R.id.deseos_prioridad_in)
            var formato = ContextThemeWrapper(this, R.style.PopupMenu)
            PopupMenu(formato, prioridad).apply {
                menuInflater.inflate(R.menu.prioridad, menu)
                setOnMenuItemClickListener { item ->
                    prioridad.setText(item.title)
                    true
                }
                show()
            }
        }

        //Guardar deseo
        binding.deseosAd.setOnClickListener {

            val ocultarTeclado: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            ocultarTeclado.hideSoftInputFromWindow(view.windowToken, 0)

            val juego = binding.deseosJuegoIn.text.toString()
            val autor = binding.deseosAutorIn.text.toString()
            val editorial = binding.deseosEditorialIn.text.toString()
            val prioridad = binding.deseosPrioridadIn.text.toString()

            //Aviso campos vacios
            if (juego.isBlank() || autor.isBlank() || editorial.isBlank() || prioridad.isBlank()) {
                Snackbar.make(view, "¡Todos los campos deben estar cubiertos!", Snackbar.LENGTH_SHORT).show()

                return@setOnClickListener

            } else {

                mDeseo.agregarDeseo(Deseo(prioridad,juego,editorial,autor)).observe(this, {
                    rAdapter.agregarDeseo(it)
                })
                Snackbar.make(view, "El juego ha sido guardado", Snackbar.LENGTH_LONG).show()
            }
        }
    }

    override fun clickDeseo(item: Deseo, position: Int) {

        binding.deseosBorrar.setOnClickListener {
            val mDeseo = ViewModelProvider(this).get(DeseoViewModel::class.java)
            mDeseo.borrarDeseo(item)
            rAdapter.borrarDeseo(position)
        }
    }

}