package com.example.proyecto.ui.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto.adapters.RecyclerViewAdapterJuego
import com.example.proyecto.adapters.juegoListener
import com.example.proyecto.databinding.FragmentJuegosBinding
import com.example.proyecto.db.entities.Juego
import com.example.proyecto.db.projections.JuegoProjection
import com.example.proyecto.ui.activities.Juegos
import com.example.proyecto.viewModel.JuegoViewModel

class JuegosFragment : Fragment(), juegoListener{

    private lateinit var rAdapter:RecyclerViewAdapterJuego
    private var _binding:FragmentJuegosBinding? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        //Inflamos el layout para el fragment
        _binding = FragmentJuegosBinding.inflate(layoutInflater)
        val view = _binding!!.root

        val model = ViewModelProvider(this).get(JuegoViewModel::class.java)
        model.mostrarListado().observe(viewLifecycleOwner,{
            createRecyclerView(it)
        })


        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }

    //Llamamos al RecyclerViewAdapter y montamos la vista
    private fun createRecyclerView(juegos:List<JuegoProjection>){

        rAdapter = RecyclerViewAdapterJuego(juegos, this)
        val recyclerView = _binding!!.juegosRecyclerview
        recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext(),RecyclerView.VERTICAL, false)
            adapter = rAdapter
            addItemDecoration(DividerItemDecoration(requireContext(),RecyclerView.VERTICAL))
        }
    }

    //Sobreescribe la funcion onclick del elemento
    override fun clickJuego(item: JuegoProjection, position: Int) {
        val intent = Intent(requireContext(), Juegos::class.java)
        intent.putExtra("id",item.id)
        intent.putExtra("portada", item.portada)
        intent.putExtra("nombre", item.nombre)
        intent.putExtra("autor", item.nombre_autor)
        intent.putExtra("editorial", item.nombre_editorial)
        startActivity(intent)
    }
}