package com.example.proyecto.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.proyecto.ui.fragments.AutorFragment
import com.example.proyecto.ui.fragments.EditorialFragment
import com.example.proyecto.ui.fragments.JuegosFragment
import com.example.proyecto.R
import com.example.proyecto.databinding.ActivityMainBinding
import com.example.proyecto.db.AppDataBase
import com.example.proyecto.db.entities.*
import com.example.proyecto.viewModel.JuegoViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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

        val dbDes = AppDataBase.newdb(this)
        //cambiarFragment(juegosFragment)
//        val frTransaction = supportFragmentManager.beginTransaction()
//        frTransaction.replace(R.id.main_contenedor, juegosFragment)
//        frTransaction.addToBackStack(null)
//        frTransaction.commit()
        /*val mJuego = ViewModelProvider(this).get(JuegoViewModel::class.java)
        mJuego.agregarJuego(Juego("prueba","caca","300","malo","mucho",1,1))

*/

        val deseo1 = Deseo("alta", "atrapa la caca", "Devir", "Cacotas")
        val deseo2 = Deseo("muy alta", "Terraforming", "Maldito", "Frixelius")

        val autor1 = Autor("Frixelius")
        val autor2 = Autor("Bauza")

        val editorial1 = Editorial("Maldito")
        val editorial2 = Editorial("Devir")

        //val nota1 = Nota("Me encata, muy bonito",1)
        //val nota2 = Nota("Que juego mas feo",1)

        val juego1 = Juego("FOTICO", "Terraforming", "0-15 Min", "Eurogame", "Hasta 4", 1, 1)
        /*val juego2 = Juego("FOTAZA", "Last Bastion", "0-15 Min", "Eurogame", "Hasta 4", 1, 1)
        val juego3 = Juego("AAAAA", "Civilization", "15-30 Min", "Eurogame", "Hasta 5", 1, 1)
        val juego4 = Juego("BBBBB", "Arriba y abajo", "30-60 Min", "Eurogame", "Hasta 4", 1, 1)
*/
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                dbDes.deseoDao().insert(deseo1)
                dbDes.deseoDao().insert(deseo2)
                dbDes.autorDao().insert(autor1)
                dbDes.autorDao().insert(autor2)
                dbDes.editorialDao().insert(editorial1)
                dbDes.editorialDao().insert(editorial2)
                //dbDes.notaDao().insert(nota1)
                //dbDes.notaDao().insert(nota2)
                dbDes.juegoDao().insert(juego1)
               /* dbDes.juegoDao().insert(juego2)
                dbDes.juegoDao().insert(juego3)
                dbDes.juegoDao().insert(juego4)
*/
/*                val lista = mutableListOf(dbDes.editorialDao().mostrarJuegosPorEditorial(1))
                lista.forEach {
                    println(it)
                }*/
                /*val partida = mutableListOf(dbDes.juegoDao().buscarPartida("Hasta 5", "3 horas"))
                partida.forEach {
                    println(it)
                }
*//*                val lista2 = mutableListOf(dbDes.notaDao().mostrarNotasPorJuego(1))
                lista2.forEach {
                    println(it)
                }*//*
                val lista3 = mutableListOf(dbDes.autorDao().mostrarJuegosPorAutor(1))
                lista3.forEach {
                    println(it)
                }*/

            }
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

