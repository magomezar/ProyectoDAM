package com.example.proyecto

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.proyecto.databinding.ActivityEditorialesBinding

class Editoriales : AppCompatActivity() {

    private lateinit var binding: ActivityEditorialesBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEditorialesBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.editorialesBack.setOnClickListener {
            val intent= Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
    }
}