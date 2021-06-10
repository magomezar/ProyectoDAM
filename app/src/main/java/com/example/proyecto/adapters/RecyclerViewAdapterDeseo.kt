package com.example.proyecto.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto.databinding.DeseoRecyclerviewBinding
import com.example.proyecto.db.entities.Deseo


class RecyclerViewAdapterDeseo(val deseo:MutableList<Deseo>, var deseoListenerSet: deseoListener): RecyclerView.Adapter<RecyclerViewAdapterDeseo.ViewHolder>() {

    class ViewHolder private constructor(val binding: DeseoRecyclerviewBinding): RecyclerView.ViewHolder(binding.root){

        fun recibirDeseos(deseo: Deseo, action: deseoListener){
            binding.registroDeseoNombre.text = deseo.nomJuego
            binding.registroDeseoAutor.text = "Autor: " + deseo.nomAutor
            binding.registroDeseoEditorial.text = "Ed: " + deseo.nomEditorial
            binding.registroDeseoPrioridad.text ="Prioridad: " + deseo.prioridad

            itemView.setOnClickListener{
                action.clickDeseo(deseo,adapterPosition)
            }
        }

        companion object {
            fun crearViewHolder(parent: ViewGroup): ViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = DeseoRecyclerviewBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder.crearViewHolder(parent)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.recibirDeseos(deseo[position],deseoListenerSet)

    override fun getItemCount() = deseo.size

    fun agregarDeseo(deseos: Deseo) {
        val _deseos = Deseo(deseos.prioridad,deseos.nomJuego,deseos.nomEditorial,deseos.nomAutor)
        this.deseo.add(_deseos)
        notifyItemInserted(this.deseo.size-1)
    }

    fun borrarDeseo(position: Int) {
        this.deseo.removeAt(position)
        notifyItemRemoved(position)
    }
}
interface deseoListener {
    fun clickDeseo(item: Deseo, position: Int)

}