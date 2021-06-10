package com.example.proyecto.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyecto.db.app.AppActivity
import com.example.proyecto.db.entities.Deseo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DeseoViewModel: ViewModel() {

    //Enlace de la base de datos
    val db = AppActivity.getDatabase()

    //Inicializador
    private val _deseos: MutableLiveData<List<Deseo>> by lazy{
        MutableLiveData<List<Deseo>>().also {
            listadoDeseos()
        }
    }

    private fun listadoDeseos(){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                val listado=db!!.deseoDao().mostrarTodo()
                _deseos.postValue(listado)
            }
        }
    }

    fun mostrarDeseos(): LiveData<List<Deseo>> = _deseos

    fun agregarDeseo(deseo: Deseo): LiveData<Deseo>{
        val lived = MutableLiveData<Deseo>()
        viewModelScope.launch {
            val addDeseo = withContext(Dispatchers.IO) {
                db!!.deseoDao().insert(deseo)
            }
            deseo.id = addDeseo
            lived.postValue(deseo)
        }
        return lived
    }

    fun borrarDeseo(deseo: Deseo){
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                db!!.deseoDao().delete(deseo)
            }
        }
    }
}