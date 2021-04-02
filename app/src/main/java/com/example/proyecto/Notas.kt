package com.example.proyecto

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.proyecto.databinding.ActivityNotasBinding

class Notas : AppCompatActivity() {

    private lateinit var binding: ActivityNotasBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNotasBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        //Boton vuelta a pantalla principal
        binding.notasBack.setOnClickListener {
            val intent= Intent(this,MainActivity::class.java)
            startActivity(intent)
        }

    }
}