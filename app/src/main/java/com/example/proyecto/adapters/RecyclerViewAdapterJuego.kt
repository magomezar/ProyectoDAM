package com.example.proyecto.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto.databinding.JuegoRecyclerviewBinding
import com.example.proyecto.db.projections.JuegoProjection

class RecyclerViewAdapterJuego(val juego:List<JuegoProjection>, var juegoListenerSet: juegoListener): RecyclerView.Adapter<RecyclerViewAdapterJuego.ViewHolder>() {

    class ViewHolder private constructor(val binding: JuegoRecyclerviewBinding): RecyclerView.ViewHolder(binding.root){

        fun recibirJuegos(juego: JuegoProjection, action: juegoListener){
            binding.registroJuegoNombre.text = juego.nombre
            binding.registroJuegoAutor.text = "Au: " + juego.nombre_autor
            binding.registroJuegoEditorial.text = "Ed: " + juego.nombre_editorial

            itemView.setOnClickListener{
                action.clickJuego(juego,adapterPosition)
            }
        }

        companion object {
            fun crearViewHolder(parent: ViewGroup): ViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = JuegoRecyclerviewBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder.crearViewHolder(parent)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.recibirJuegos(juego[position],juegoListenerSet)

    override fun getItemCount() = juego.size
}

interface juegoListener {
    fun clickJuego(item:JuegoProjection, position: Int)
}