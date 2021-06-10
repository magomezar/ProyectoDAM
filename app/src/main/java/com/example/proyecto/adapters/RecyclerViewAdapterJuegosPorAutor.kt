package com.example.proyecto.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto.databinding.AutorJuegosRecyclerviewBinding
import com.example.proyecto.db.projections.JuegosPorAutor

class RecyclerViewAdapterJuegosPorAutor (val juegosPorAutor:List<JuegosPorAutor>, var juegosPorAutorListenerSet: juegosPorAutorListener): RecyclerView.Adapter<RecyclerViewAdapterJuegosPorAutor.ViewHolder>(){

    class ViewHolder private constructor(val binding: AutorJuegosRecyclerviewBinding): RecyclerView.ViewHolder(binding.root){

        fun recibirJuegos(autor: JuegosPorAutor, action: juegosPorAutorListener){
            binding.registroAutorNombre.text = autor.nombre

            itemView.setOnClickListener {
                action.clickJuegoAutor(autor,adapterPosition)
            }
        }

        companion object {
            fun crearViewHolder(parent: ViewGroup): ViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = AutorJuegosRecyclerviewBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder.crearViewHolder(parent)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.recibirJuegos(juegosPorAutor[position],juegosPorAutorListenerSet)

    override fun getItemCount() = juegosPorAutor.size
}
interface juegosPorAutorListener {
    fun clickJuegoAutor(item: JuegosPorAutor, position: Int)
}

