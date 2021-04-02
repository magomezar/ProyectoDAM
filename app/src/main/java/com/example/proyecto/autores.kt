package com.example.proyecto

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.proyecto.databinding.ActivityAutoresBinding

class autores : AppCompatActivity() {

    private lateinit var binding: ActivityAutoresBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAutoresBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.autoresBack.setOnClickListener {
            val intent= Intent(this,MainActivity::class.java)
            startActivity(intent)
        }


    }
}