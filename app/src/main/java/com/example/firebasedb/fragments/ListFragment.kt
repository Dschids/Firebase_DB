package com.example.firebasedb.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.firebasedb.R
import com.example.firebasedb.databinding.FragmentListBinding

class ListFragment : Fragment() {

    private lateinit var _list_binding: FragmentListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _list_binding = FragmentListBinding.inflate(inflater, container, false)
        val view = _list_binding.root

        _list_binding.fabAdd.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_addFragment)
        }



        return view
    }


}