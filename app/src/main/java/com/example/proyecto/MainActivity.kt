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

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.mainGuardarJuegos.setOnClickListener{
            val intent= Intent(this,juegos::class.java)
            startActivity(intent)
            //Toast de entrada a la activity2
            val toast= Toast.makeText(this, "¡Bienvenido a Juegos!", Toast.LENGTH_SHORT)
            toast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 600)
            toast.show()
        }

        binding.mainDeseos.setOnClickListener {
            val intent= Intent(this,deseos::class.java)
            startActivity(intent)
            //Toast de entrada a la activity2
            val toast= Toast.makeText(this, "¡PRUEBAS!", Toast.LENGTH_SHORT)
            toast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 600)
            toast.show()
        }


    }
}