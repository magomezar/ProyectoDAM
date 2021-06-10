package com.example.proyecto.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyecto.db.app.AppActivity
import com.example.proyecto.db.entities.Autor
import com.example.proyecto.db.projections.JuegosPorAutor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AutorViewModel: ViewModel() {

    //Enlace de la base de datos
    val db = AppActivity.getDatabase()

    //Inicializador
    private val _autores: MutableLiveData<List<Autor>> by lazy{
        MutableLiveData<List<Autor>>().also {

            listadoAutores()
        }
    }

    private fun listadoAutores(){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                val listado=db!!.autorDao().mostrarTodo()
                _autores.postValue(listado)
            }
        }
    }

    fun mostrarAutores(): LiveData<List<Autor>> = _autores

    fun buscarAutor(nombre: String): LiveData<Long>{
        val lived = MutableLiveData<Long>()
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                val autor= db!!.autorDao().buscarAutor(nombre)
                if(autor!=null){
                    lived.postValue(autor.id)
                }else{
                    lived.postValue(-1)
                }
            }
        }
        return lived
    }

    fun agregarAutor(autor: Autor){
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                db!!.autorDao().insert(autor)
            }
        }
    }

    fun buscarAutorId(id: Long): LiveData<Autor>{
        val lived = MutableLiveData<Autor>()
        viewModelScope.launch {
            val juegos = withContext(Dispatchers.IO) {
                db!!.autorDao().mostrarPorId(id)
            }
            lived.postValue(juegos)
        }
        return lived
    }

    fun borrarAutor(autor: Autor){
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                db!!.autorDao().delete(autor)
            }
        }
    }

    fun buscarJuegos(id: Long): LiveData<List<JuegosPorAutor>>{
        val lived = MutableLiveData<List<JuegosPorAutor>>()
        viewModelScope.launch {
            val juegos = withContext(Dispatchers.IO) {
                db!!.autorDao().mostrarJuegosPorAutor(id)
            }
            lived.postValue(juegos)
        }
        return lived
    }
}

