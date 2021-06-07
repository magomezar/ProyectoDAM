package com.example.proyecto.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.proyecto.ui.fragments.AutorFragment
import com.example.proyecto.ui.fragments.EditorialFragment
import com.example.proyecto.ui.fragments.JuegosFragment
import com.example.proyecto.R
import com.example.proyecto.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar


class MainActivity : AppCompatActivity() {

    var juegosFragment = JuegosFragment()
    var autoresFragment = AutorFragment()
    var editorialesFragment = EditorialFragment()
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Base_MyTheme)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        if (intent.extras!=null){
            val agregar = intent.extras?.get("agregar")
            Snackbar.make(view, "${agregar.toString()}", Snackbar.LENGTH_LONG).show()
        }

                binding.mainJuegos.setOnClickListener {
                    cambiarFragment(juegosFragment)
                }

                binding.mainAutores.setOnClickListener {
                    cambiarFragment(autoresFragment)
                }

                binding.mainEditoriales.setOnClickListener {
                    cambiarFragment(editorialesFragment)
                }

                binding.mainGuardarJuegos.setOnClickListener {
                    val intent = Intent(this, Juegos::class.java)
                    startActivity(intent)
                }

                binding.mainDeseos.setOnClickListener {
                    val intent = Intent(this, Deseos::class.java)
                    startActivity(intent)
                }

                binding.mainPartidas.setOnClickListener {
                    val intent = Intent(this, Partidas::class.java)
                    startActivity(intent)

                }
            }

            private fun cambiarFragment(fragment: Fragment) {
                val frTransaction = supportFragmentManager.beginTransaction()
                frTransaction.replace(R.id.main_contenedor, fragment)
                frTransaction.addToBackStack(null)
                frTransaction.commit()
            }
        }

