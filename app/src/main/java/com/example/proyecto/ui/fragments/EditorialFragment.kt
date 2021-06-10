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
import com.example.proyecto.adapters.RecyclerViewAdapterEditorial
import com.example.proyecto.adapters.editorialListener
import com.example.proyecto.databinding.FragmentEditorialBinding
import com.example.proyecto.db.entities.Editorial
import com.example.proyecto.ui.activities.Editoriales
import com.example.proyecto.viewModel.EditorialViewModel


class EditorialFragment : Fragment(), editorialListener {

    private lateinit var rAdapter: RecyclerViewAdapterEditorial
    private var _binding: FragmentEditorialBinding? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        //Inflamos el layout para el fragment
        _binding = FragmentEditorialBinding.inflate(layoutInflater)
        val view = _binding!!.root

        val model = ViewModelProvider(this).get(EditorialViewModel::class.java)
        model.mostrarEditoriales().observe(viewLifecycleOwner,{
            createRecyclerView(it)
        })

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }

    //Llamamos al RecyclerViewAdapter y montamos la vista
    private fun createRecyclerView(editoriales:List<Editorial>){

        rAdapter = RecyclerViewAdapterEditorial(editoriales, this)
        val recyclerView = _binding!!.editorialesRecyclerview
        recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            adapter = rAdapter
            addItemDecoration(DividerItemDecoration(requireContext(), RecyclerView.VERTICAL))
        }
    }

    //Sobreescribe la funcion onclick del elemento
    override fun clickEditorial(item: Editorial, position: Int) {
        val intent = Intent(requireContext(), Editoriales::class.java)
        intent.putExtra("nombre", item.nombreEditorial)
        startActivity(intent)
    }
}