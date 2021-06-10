package com.example.proyecto.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyecto.db.app.AppActivity
import com.example.proyecto.db.entities.Juego
import com.example.proyecto.db.entities.Nota
import com.example.proyecto.db.projections.JuegoProjection
import com.example.proyecto.db.projections.JuegosPartida
import com.example.proyecto.db.projections.NotasPorJuego
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class  JuegoViewModel: ViewModel() {

    //Enlace de la base de datos
    val db = AppActivity.getDatabase()

    //Inicializador
    private val _juegos: MutableLiveData<List<JuegoProjection>> by lazy {
        MutableLiveData<List<JuegoProjection>>().also {

            listadoJuegos()
        }
    }

    private fun listadoJuegos() {
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                val listado=db!!.juegoDao().mostrarProjectionJuego()
                _juegos.postValue(listado)
            }
        }
    }

    fun mostrarListado(): LiveData<List<JuegoProjection>> = _juegos

    fun agregarJuego(juego: Juego){
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                db!!.juegoDao().insert(juego)
            }
        }
    }

    fun buscarJuego(id:Long): LiveData<Long> {
        val lived = MutableLiveData<Long>()
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                val juego= db!!.juegoDao().mostrarPorId(id)
                if(juego!=null){
                    lived.postValue(juego.id)
                }else{
                    lived.postValue(-1)
                }
            }
        }
        return lived
    }

    fun buscarIdJuego(nombre: String): LiveData<Long> {
        val lived = MutableLiveData<Long>()
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                val id= db!!.juegoDao().buscarId(nombre)
                if(id!=null){
                    lived.postValue(id!!)
                }else{
                    lived.postValue(-1)
                }
            }
        }
        return lived
    }

    fun juegoCompleto(nombre: String): LiveData<Juego> {
        val lived = MutableLiveData<Juego>()
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                val juego= db!!.juegoDao().buscarJuego(nombre)
                if(juego!=null){
                    lived.postValue(juego!!)
                }
            }
        }
        return lived
    }

    fun actualizarJuego(nombre:String,duracion:String,categoria:String,jugadores:String,idAu:Long,idEd:Long,id:Long) {
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                db!!.juegoDao().actualizarJuego(nombre,duracion,categoria,jugadores,idAu,idEd,id)
            }
        }
    }

    fun borrarJuego(juego: Juego) {
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                db!!.juegoDao().delete(juego)
            }
        }
    }

    fun buscarPartida(jugadores: String, tiempo: String): LiveData<List<JuegosPartida>> {
        val lived = MutableLiveData<List<JuegosPartida>>()
        viewModelScope.launch {
            val partida = withContext(Dispatchers.IO){
                db!!.juegoDao().buscarPartida(jugadores, tiempo)
            }
            lived.postValue(partida)
        }
        return lived
    }

    fun agregarNotasJuego(nota: Nota): LiveData<Nota> {
        val lived = MutableLiveData<Nota>()
        viewModelScope.launch {
            val addNota = withContext(Dispatchers.IO) {
                db!!.notaDao().insert(nota)
            }
            nota.id = addNota
            lived.postValue(nota)
        }
        return lived
    }

    fun mostrarNotasPorJuego(id: Long): LiveData<List<NotasPorJuego>> {
        val lived = MutableLiveData<List<NotasPorJuego>>()
        viewModelScope.launch {
            val nota = withContext(Dispatchers.IO){
                db!!.notaDao().mostrarNotasPorJuego(id)

            }
            lived.postValue(nota)
        }
        return lived
    }

    fun borrarNotas(id: Long) {
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                db!!.notaDao().borrarNotas(id)
            }
        }
    }

    fun buscarPortada(id: Long): LiveData<String>{
        val liveData= MutableLiveData<String>()
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val nombre = db!!.juegoDao().mostrarPorId(id)
                if (nombre != null) {
                    liveData.postValue(nombre.portada)
                }
            }
        }
        return liveData
    }

    fun actualizarPortada(imagen: String,id:Long){
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                db!!.juegoDao().actualizarPortada(imagen,id)
            }
        }
    }
}