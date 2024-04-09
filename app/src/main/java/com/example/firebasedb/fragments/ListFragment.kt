package com.example.firebasedb.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.firebasedb.ListAdapter
import com.example.firebasedb.Person
import com.example.firebasedb.R
import com.example.firebasedb.databinding.FragmentListBinding
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.google.firebase.database.getValue

class ListFragment : Fragment() {

    private lateinit var _list_binding: FragmentListBinding
    private lateinit var database: DatabaseReference

    val changeListener: ValueEventListener = object: ValueEventListener{
        override fun onDataChange(snapshot: DataSnapshot) {
            if (snapshot.hasChildren()){
                var count = snapshot.childrenCount
                for (child in snapshot.children){
                    val holddata = child.getValue(Person::class.java)

                }
            }
        }

        override fun onCancelled(error: DatabaseError) {
            TODO("Not yet implemented")
        }
    }

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

        // set the adapter to the ListAdapter I made
        val adapter = ListAdapter()
        // create a recyclerview variable for ease of use (don't have to keep typing _binding.rvlist)
        val recyclerView = _list_binding.rvList
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        database = Firebase.database.getReference("/users")
        database.addValueEventListener(changeListener)


        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        database.removeEventListener(changeListener)
    }

}