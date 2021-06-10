package com.example.proyecto.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto.databinding.EditorialJuegosRecyclerviewBinding
import com.example.proyecto.db.projections.JuegosPorEditorial

class RecyclerViewAdapterJuegosPorEditorial (val juegosPorEditorial:List<JuegosPorEditorial>, var juegosPorEditorialListenerSet: juegosPorEditorialListener): RecyclerView.Adapter<RecyclerViewAdapterJuegosPorEditorial.ViewHolder>() {

    class ViewHolder private constructor(val binding: EditorialJuegosRecyclerviewBinding): RecyclerView.ViewHolder(binding.root){

        fun recibirJuegos(editorial: JuegosPorEditorial, action: juegosPorEditorialListener){
            binding.registroEditorialNombre.text = editorial.nombre

            itemView.setOnClickListener {
                action.clickJuegoEditorial(editorial,adapterPosition)
            }
        }

        companion object {
            fun crearViewHolder(parent: ViewGroup): ViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = EditorialJuegosRecyclerviewBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder.crearViewHolder(parent)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.recibirJuegos(juegosPorEditorial[position],juegosPorEditorialListenerSet)

    override fun getItemCount() = juegosPorEditorial.size
}
interface juegosPorEditorialListener {
    fun clickJuegoEditorial(item: JuegosPorEditorial, position: Int)
}