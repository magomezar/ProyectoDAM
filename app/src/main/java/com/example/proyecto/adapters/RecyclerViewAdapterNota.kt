package com.example.proyecto.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto.databinding.NotaRecyclerviewBinding

import com.example.proyecto.db.entities.Nota
import com.example.proyecto.db.projections.NotasPorJuego

class RecyclerViewAdapterNota(val nota:MutableList<NotasPorJuego>, var notaListenerSet: notaListener): RecyclerView.Adapter<RecyclerViewAdapterNota.ViewHolder>() {

    class ViewHolder private constructor(val binding: NotaRecyclerviewBinding): RecyclerView.ViewHolder(binding.root){

        fun recibirNotas(nota: NotasPorJuego, action: notaListener){
            binding.registroNota.text = nota.nota

            itemView.setOnClickListener{
                action.clickNota(nota,adapterPosition)
            }
        }

        companion object {
            fun crearViewHolder(parent: ViewGroup): ViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = NotaRecyclerviewBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder.crearViewHolder(parent)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.recibirNotas(nota[position],notaListenerSet)

    override fun getItemCount() = nota.size

    fun agregarNota(notas: Nota) {
        val _notas = NotasPorJuego(notas.nota,notas.id)
        this.nota.add(_notas)
        notifyItemInserted(this.nota.size-1)
    }

    fun borrarNota(position: Int) {
        this.nota.removeAt(position)
        notifyItemRemoved(position)
    }

}
interface notaListener {
    fun clickNota(item: NotasPorJuego, position: Int)


}