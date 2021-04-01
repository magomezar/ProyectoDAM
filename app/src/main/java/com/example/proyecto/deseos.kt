package com.example.proyecto

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.appcompat.widget.PopupMenu

class deseos : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_deseos)

        val editText = findViewById<EditText>(R.id.prueba)
        editText.setOnClickListener {
            PopupMenu(this, editText).apply {
                menuInflater.inflate(R.menu.categorias, menu)
                setOnMenuItemClickListener { item ->
                    editText.setText(item.title)
                    true
                }
                show()
            }
        }

    }
}