package com.example.proyecto

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.Toast
import com.example.proyecto.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setTheme(R.style.Base_MyTheme)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.mainGuardarJuegos.setOnClickListener{
            val intent= Intent(this,juegos::class.java)
            startActivity(intent)
        }

        binding.mainDeseos.setOnClickListener {
            val intent= Intent(this,deseos::class.java)
            startActivity(intent)
        }

        binding.mainPartidas.setOnClickListener {
            val intent= Intent(this,notas::class.java)
            startActivity(intent)

        }


    }
}