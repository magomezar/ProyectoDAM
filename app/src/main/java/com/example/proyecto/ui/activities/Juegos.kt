package com.example.proyecto.ui.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.ContextThemeWrapper
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModelProvider
import com.example.proyecto.R
import com.example.proyecto.databinding.ActivityJuegosBinding
import com.example.proyecto.db.entities.Autor
import com.example.proyecto.db.entities.Editorial
import com.example.proyecto.db.entities.Juego
import com.example.proyecto.viewModel.AutorViewModel
import com.example.proyecto.viewModel.EditorialViewModel
import com.example.proyecto.viewModel.JuegoViewModel
import com.google.android.material.snackbar.Snackbar
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class Juegos : AppCompatActivity() {

    private lateinit var binding: ActivityJuegosBinding
    val TAG = "JUEGOS"
    val REQUEST_TAKE_PHOTO = 1
    var currentPhotoPath:String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityJuegosBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        //Log.d(TAG, currentPhotoPath)
        val mJuego = ViewModelProvider(this).get(JuegoViewModel::class.java)
        val mAutor = ViewModelProvider(this).get(AutorViewModel::class.java)
        val mEditorial = ViewModelProvider(this).get(EditorialViewModel::class.java)

        if (intent.extras!=null){
            val nombreFicha = intent.extras?.get("nombre")

            binding.juegosNombreIn.setText(nombreFicha.toString())

            if(intent.extras?.get("autor")==null) {
                mJuego.juegoCompleto(nombreFicha.toString()).observe(this, {
                    val juego = it
                    mAutor.buscarAutorId(juego.idAutor).observe(this, {
                        binding.juegosAutorIn.setText(it.nombreAutor)
                    })
                })
            } else {
            binding.juegosAutorIn.setText(intent.extras?.get("autor").toString())
            }

            if(intent.extras?.get("editorial")==null) {
                mJuego.juegoCompleto(nombreFicha.toString()).observe(this, {
                    val juego = it
                    mEditorial.buscarEditorialId(juego.idEditorial).observe(this,{
                        binding.juegosEditorialIn.setText(it.nombreEditorial)
                    })
                })
            } else {
                binding.juegosEditorialIn.setText(intent.extras?.get("editorial").toString())
            }

            mJuego.juegoCompleto(nombreFicha.toString()).observe(this, {
                val juego = it
                binding.juegosJugadoresIn.setText(juego.jugadores)
                binding.juegosCategoriasIn.setText(juego.categoria)
                binding.juegosDuracionIn.setText(juego.duracion)

                if (intent.extras?.getString("portada") != null){
                    val portada = intent.extras?.getString("portada")
                    if(!portada!!.isEmpty()) {
                        currentPhotoPath = portada.toString()
                        binding.juegosImagen.setImageURI(Uri.fromFile(File(portada)))
                    }else {
                        binding.juegosImagen.setImageDrawable(ContextCompat.getDrawable(applicationContext,R.drawable.ic_camara_icono))
                    }
                }else {
                    mJuego.buscarPortada(juego.id).observe(this, {
                        if(!it.isEmpty()){
                            binding.juegosImagen.setImageURI(Uri.fromFile(File(it)))
                        }else
                            binding.juegosImagen.setImageDrawable(ContextCompat.getDrawable(applicationContext,R.drawable.ic_camara_icono))
                    })
                }
            })
        }


        //Boton vuelta a pantalla principal
        binding.juegosBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        //Desplegable de categorias
        binding.juegosCategoriasIn.setOnClickListener {
            val categorias = findViewById<EditText>(R.id.juegos_categorias_in)
            var formato = ContextThemeWrapper(this, R.style.PopupMenu)
            PopupMenu(formato, categorias).apply {
                menuInflater.inflate(R.menu.categorias, menu)
                setOnMenuItemClickListener { item ->
                    categorias.setText(item.title)
                    true
                }
                show()
            }
        }

        //Desplegable para la duracion
        binding.juegosDuracionIn.setOnClickListener {
            val duracion = findViewById<EditText>(R.id.juegos_duracion_in)
            var formato = ContextThemeWrapper(this, R.style.PopupMenu)
            PopupMenu(formato, duracion).apply {
                menuInflater.inflate(R.menu.duracion, menu)
                setOnMenuItemClickListener { item ->
                    duracion.setText(item.title)
                    true
                }
                show()
            }
        }

        //Desplegable para el numero de jugadores
        binding.juegosJugadoresIn.setOnClickListener {
            val jugadores = findViewById<EditText>(R.id.juegos_jugadores_in)
            var formato = ContextThemeWrapper(this, R.style.PopupMenu)
            PopupMenu(formato, jugadores).apply {
                menuInflater.inflate(R.menu.jugadores, menu)
                setOnMenuItemClickListener { item ->
                    jugadores.setText(item.title)
                    true
                }
                show()
            }
        }

        //Guardar autor nuevo
        binding.imageButtonAutor.setOnClickListener {
            val autor = binding.juegosAutorIn.text.toString()
            mAutor.buscarAutor(autor).observe(this, { idAu ->
                if (idAu != -1L) {
                    Snackbar.make(view, "Autor ya creado", Snackbar.LENGTH_SHORT).show()
                    return@observe
                }
                mAutor.agregarAutor(Autor(autor))
                Snackbar.make(view, "El autor ha sido creado", Snackbar.LENGTH_SHORT).show()
            })
        }

        //Guardar editorial nueva
        binding.imageButtonEditorial.setOnClickListener {
            val editorial = binding.juegosEditorialIn.text.toString()
            mEditorial.buscarEditorial(editorial).observe(this, { idEd ->
                if (idEd != -1L) {
                    Snackbar.make(view, "Editorial ya creada", Snackbar.LENGTH_SHORT).show()
                    return@observe
                }
                mEditorial.agregarEditorial(Editorial(editorial))
                Snackbar.make(view, "La editorial ha sido creada", Snackbar.LENGTH_SHORT).show()
            })
        }

        //Guardar portada
        binding.juegosImagen.setOnClickListener {
            dispatchTakePictureIntent()
        }

        //Guardar juego
        binding.juegosGuardar.setOnClickListener {

            //Variables de los Editext
            val portada = currentPhotoPath
            val nombre = binding.juegosNombreIn.text.toString()
            val autor = binding.juegosAutorIn.text.toString()
            val editorial = binding.juegosEditorialIn.text.toString()
            val jugadores = binding.juegosJugadoresIn.text.toString()
            val duracion = binding.juegosDuracionIn.text.toString()
            val categoria = binding.juegosCategoriasIn.text.toString()
            //Log.d(TAG,portada)

            //Aviso campos vacios
            if (nombre.isBlank() || autor.isBlank() || editorial.isBlank() || jugadores.isBlank() || duracion.isBlank() || categoria.isBlank()) {
                Snackbar.make(view, "¡Todos los campos deben estar cubiertos!", Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            //Comprobacion de datos y guardar juego
            else {
                    mAutor.buscarAutor(autor).observe(this, { idAu ->
                        if (idAu == -1L) {
                            Snackbar.make(view, "Este autor no existe. Agrega primero el autor", Snackbar.LENGTH_LONG).show()
                            return@observe
                        }
                        mEditorial.buscarEditorial(editorial).observe(this, { idEd ->
                           if (idEd == -1L) {
                                Snackbar.make(view, "Esta editorial no existe. Agrega primero la editorial", Snackbar.LENGTH_LONG).show()
                                return@observe
                           }

                            //mJuego.juegoCompleto(nombre).observe(this,{
                                mJuego.buscarIdJuego(nombre).observe(this,{ idJ ->
                                    Log.d(TAG,idJ.toString())
                                    if(idJ == -1L){
                                        mJuego.agregarJuego(Juego(portada, nombre, duracion, categoria, jugadores, idAu, idEd))
                                        Snackbar.make(view, "El juego ha sido guardado", Snackbar.LENGTH_LONG).show()
                                        val intent = Intent(this, MainActivity::class.java)
                                        intent.putExtra("agregar", "El juego ha sido agregado")
                                        startActivity(intent)
                                    }else{
                                        Snackbar.make(view, "El juego ya existe", Snackbar.LENGTH_LONG).show()
                                        return@observe
                                    }
                                })
                           })
                    })
            }
        }

        binding.juegosModificar.setOnClickListener {

            if (intent.extras!=null) {

                //Variables de los Editext
                val portada = currentPhotoPath
                val nombre = binding.juegosNombreIn.text.toString()
                val autor = binding.juegosAutorIn.text.toString()
                val editorial = binding.juegosEditorialIn.text.toString()
                val jugadores = binding.juegosJugadoresIn.text.toString()
                val duracion = binding.juegosDuracionIn.text.toString()
                val categoria = binding.juegosCategoriasIn.text.toString()
                //Log.d(TAG,portada)

                //Aviso campos vacios
                if (nombre.isBlank() || autor.isBlank() || editorial.isBlank() || jugadores.isBlank() || duracion.isBlank() || categoria.isBlank()) {
                    Snackbar.make(view, "¡Todos los campos deben estar cubiertos!", Snackbar.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                //Comprobacion de datos y guardar juego
                else {
                    val idJuego = intent.extras?.getLong("id")
                    mJuego.buscarJuego(idJuego!!.toLong()).observe(this, { idJu ->

                        mAutor.buscarAutor(autor).observe(this, { idAu ->
                            if (idAu == -1L) {
                                Snackbar.make(view, "Este autor no existe. Agrega primero el autor", Snackbar.LENGTH_LONG).show()
                                return@observe
                            }
                            mEditorial.buscarEditorial(editorial).observe(this, { idEd ->
                                if (idEd == -1L) {
                                    Snackbar.make(view, "Esta editorial no existe. Agrega primero la editorial", Snackbar.LENGTH_LONG).show()
                                    return@observe
                                }
                                mJuego.actualizarPortada(portada, idJu)
                                  //val act = mJuego.actualizarPortada(portada, idJu)
                                  //Log.d(TAG,act.toString())

                                mJuego.actualizarJuego(nombre, duracion, categoria, jugadores, idAu, idEd, idJu)
                                 // val actualizar = mJuego.actualizarJuego(nombre, duracion, categoria, jugadores, idAu, idEd, idJu)
                                 // Log.d(TAG, actualizar.toString())
                                Snackbar.make(view, "Se ha actualizado el juego", Snackbar.LENGTH_LONG).show()
                            })
                        })
                    })
                }

            } else{
                Snackbar.make(view, "No se puede actualizar sin guardar primero", Snackbar.LENGTH_LONG).show()
            }

        }

        //Agregar notas
        binding.juegosNotas.setOnClickListener {
            if(intent.extras!=null) {
                val nombre = binding.juegosNombreIn.text.toString()
                val intent = Intent(this, Notas::class.java)
                intent.putExtra("nombre", nombre.toString())
                startActivity(intent)
            } else {
                Snackbar.make(view, "No se puede ir a notas sin un juego agregado", Snackbar.LENGTH_LONG).show()
            }
        }

        binding.juegosBorrar.setOnClickListener {
            val nombre = binding.juegosNombreIn.text.toString()
            mJuego.juegoCompleto(nombre).observe(this, {
                mJuego.borrarJuego(it)
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            })
        }
    }


    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            // Ensure that there's a camera activity to handle the intent
            takePictureIntent.resolveActivity(packageManager)?.also {
                // Create the File where the photo should go
                var photoFile: File? = try {
                    createImageFile()
                } catch (ex: IOException) {
                    null
                }
                photoFile?.also {
                    val photoURI: Uri = FileProvider.getUriForFile(
                            this,
                            "com.example.proyecto.fileprovider",
                            it
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO)
                }
            }
        }
    }


    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        var timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        var storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
                "JPEG${timeStamp}_", /* prefix */
                ".jpg", /* suffix */
                storageDir /* directory */
        ).apply {
            currentPhotoPath = absolutePath
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            binding.juegosImagen.setImageURI(Uri.fromFile(File(currentPhotoPath)))
        }
    }


}


