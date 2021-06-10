package com.example.proyecto.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto.databinding.PartidasRecyclerviewBinding
import com.example.proyecto.db.projections.JuegosPartida

class RecyclerViewAdapterPartida(val partidas:List<JuegosPartida>): RecyclerView.Adapter<RecyclerViewAdapterPartida.ViewHolder>() {

    class ViewHolder private constructor(val binding: PartidasRecyclerviewBinding): RecyclerView.ViewHolder(binding.root){

        fun recibirPartidas(partida: JuegosPartida){
            binding.registroPartidaJuego.text = partida.nombre

        }

        companion object {
            fun crearViewHolder(parent: ViewGroup): ViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = PartidasRecyclerviewBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder.crearViewHolder(parent)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.recibirPartidas(partidas[position])

    override fun getItemCount() = partidas.size
}