package com.example.proyecto

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.appcompat.widget.PopupMenu
import com.example.proyecto.databinding.ActivityDeseosBinding

class deseos : AppCompatActivity() {

    private lateinit var binding: ActivityDeseosBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDeseosBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        //Vuelta a la pantalla principal
        binding.deseosBack.setOnClickListener {
            val intent= Intent(this,MainActivity::class.java)
            startActivity(intent)
        }

        //Desplegable de prioridades
        binding.deseosPrioridadIn.setOnClickListener {
            val prioridad = findViewById<EditText>(R.id.deseos_prioridad_in)
            PopupMenu(this, prioridad).apply {
                menuInflater.inflate(R.menu.prioridad, menu)
                setOnMenuItemClickListener { item ->
                    prioridad.setText(item.title)
                    true
                }
                show()
            }
        }




        /*val editText = findViewById<EditText>(R.id.prueba)
        editText.setOnClickListener {
            PopupMenu(this, editText).apply {
                menuInflater.inflate(R.menu.categorias, menu)
                setOnMenuItemClickListener { item ->
                    editText.setText(item.title)
                    true
                }
                show()
            }
        }*/

    }
}