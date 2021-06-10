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
import com.example.proyecto.adapters.RecyclerViewAdapterAutor
import com.example.proyecto.adapters.autorListener
import com.example.proyecto.databinding.FragmentAutorBinding
import com.example.proyecto.db.entities.Autor
import com.example.proyecto.ui.activities.Autores
import com.example.proyecto.viewModel.AutorViewModel


class AutorFragment : Fragment(), autorListener {

    private lateinit var rAdapter:RecyclerViewAdapterAutor
    private var _binding: FragmentAutorBinding? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        //Inflamos el layout para el fragment
        _binding = FragmentAutorBinding.inflate(layoutInflater)
        val view = _binding!!.root

        val model = ViewModelProvider(this).get(AutorViewModel::class.java)
        model.mostrarAutores().observe(viewLifecycleOwner,{
            createRecyclerView(it)
        })

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }

    //Llamamos al RecyclerViewAdapter y montamos la vista
    private fun createRecyclerView(autores:List<Autor>){

        rAdapter = RecyclerViewAdapterAutor(autores, this)
        val recyclerView = _binding!!.autoresRecyclerview
        recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            adapter = rAdapter
            addItemDecoration(DividerItemDecoration(requireContext(), RecyclerView.VERTICAL))
        }
    }

    //Sobreescribe la funcion onclick del elemento
    override fun clickAutor(item: Autor, position: Int) {
        val intent = Intent(requireContext(), Autores::class.java)
        intent.putExtra("nombre", item.nombreAutor)
        intent.putExtra("id_autor", item.id)
        startActivity(intent)
    }
}