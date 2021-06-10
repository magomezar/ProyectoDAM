package com.example.proyecto.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyecto.db.app.AppActivity
import com.example.proyecto.db.entities.Editorial
import com.example.proyecto.db.projections.JuegosPorEditorial
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EditorialViewModel: ViewModel() {

    //Enlace de la base de datos
    val db = AppActivity.getDatabase()

    //Inicializador
    private val _editoriales: MutableLiveData<List<Editorial>> by lazy{
        MutableLiveData<List<Editorial>>().also {

            listadoEditoriales()
        }
    }

    private fun listadoEditoriales(){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                val listado=db!!.editorialDao().mostrarTodo()
                _editoriales.postValue(listado)
            }
        }
    }

    fun mostrarEditoriales(): LiveData<List<Editorial>> = _editoriales

    fun buscarEditorial(nombre: String): LiveData<Long>{
        val lived = MutableLiveData<Long>()
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                val editorial= db!!.editorialDao().buscarEditorial(nombre)
                if(editorial!=null){
                    lived.postValue(editorial.id)
                }else{
                    lived.postValue(-1)
                }
            }
        }
        return lived
    }

    fun agregarEditorial(editorial: Editorial){
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                db!!.editorialDao().insert(editorial)
            }
        }
    }

    fun buscarEditorialId(id: Long): LiveData<Editorial>{
        val lived = MutableLiveData<Editorial>()
        viewModelScope.launch {
            val juegos = withContext(Dispatchers.IO) {
                db!!.editorialDao().mostrarPorId(id)
            }
            lived.postValue(juegos)
        }
        return lived
    }

    fun borrarEditorial(editorial: Editorial){
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                db!!.editorialDao().delete(editorial)
            }
        }
    }

    fun buscarJuegos(id: Long): LiveData<List<JuegosPorEditorial>>{
        val lived = MutableLiveData<List<JuegosPorEditorial>>()
        viewModelScope.launch {
            val juegos = withContext(Dispatchers.IO) {
                db!!.editorialDao().mostrarJuegosPorEditorial(id)
            }
            lived.postValue(juegos)
        }
        return lived
    }
}