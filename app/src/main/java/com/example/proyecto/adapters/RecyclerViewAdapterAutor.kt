package com.example.proyecto.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto.databinding.AutorRecyclerviewBinding
import com.example.proyecto.db.entities.Autor

class RecyclerViewAdapterAutor(val autor:List<Autor>, var autorListenerSet: autorListener) : RecyclerView.Adapter<RecyclerViewAdapterAutor.ViewHolder>() {

    class ViewHolder private constructor(val binding: AutorRecyclerviewBinding) : RecyclerView.ViewHolder(binding.root){

        fun recibirAutores(autor: Autor, action: autorListener){
            binding.registroAutorNombre.text = autor.nombreAutor

            itemView.setOnClickListener{
                action.clickAutor(autor,adapterPosition)
            }
        }

        companion object {
            fun crearViewHolder(parent: ViewGroup): ViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = AutorRecyclerviewBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder.crearViewHolder(parent)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.recibirAutores(autor[position],autorListenerSet)

    override fun getItemCount() = autor.size
}
interface autorListener {
    fun clickAutor(item: Autor, position: Int)
}