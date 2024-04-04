package com.example.firebasedb.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.firebasedb.Person
import com.example.firebasedb.R
import com.example.firebasedb.databinding.FragmentAddBinding
import com.google.firebase.Firebase
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database

class AddFragment : Fragment() {

    private lateinit var _add_binding: FragmentAddBinding
    private lateinit var database: DatabaseReference
    var count = 0
    lateinit var id: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _add_binding = FragmentAddBinding.inflate(inflater, container, false)
        val view = _add_binding.root

        database = Firebase.database.reference


        _add_binding.btnAdd.setOnClickListener {
            count += 1
            when (count){
                in 1..9 -> id = "000$count"
                in 10..99-> id = "00$count"
                in 100..999 -> id = "0$count"
                else -> id = "$count"
            }
            addDataToFirebase(id)
        }

        return view
    }

    private fun addDataToFirebase(id: String){
        val firstName = _add_binding.etFirstName.text.toString()
        val lastName = _add_binding.etLastName.text.toString()
        val gender = _add_binding.etGender.text.toString()
        val age = _add_binding.etAge.text.toString()
        var user = Person(firstName, lastName, gender, age)

        database.child("users").child(id).setValue(user)


    }


}