package com.example.proyecto.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto.databinding.EditorialRecyclerviewBinding
import com.example.proyecto.db.entities.Editorial

class RecyclerViewAdapterEditorial(val editorial:List<Editorial>, var editorialListenerSet: editorialListener): RecyclerView.Adapter<RecyclerViewAdapterEditorial.ViewHolder>() {

    class ViewHolder private constructor(val binding: EditorialRecyclerviewBinding): RecyclerView.ViewHolder(binding.root){

        fun recibirEditoriales(editorial: Editorial, action: editorialListener){
            binding.registroEditorialNombre.text = editorial.nombreEditorial

            itemView.setOnClickListener{
                action.clickEditorial(editorial,adapterPosition)
            }
        }

        companion object {
            fun crearViewHolder(parent: ViewGroup): ViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = EditorialRecyclerviewBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder.crearViewHolder(parent)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.recibirEditoriales(editorial[position],editorialListenerSet)

    override fun getItemCount() = editorial.size
}
interface editorialListener {
    fun clickEditorial(item: Editorial, position: Int)
}