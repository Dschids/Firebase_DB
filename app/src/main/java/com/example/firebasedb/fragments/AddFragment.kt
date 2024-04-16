package com.example.firebasedb.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
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

        /* create a shared preference file to hold the count number so it doesn't reset causing
        firebase to overwrite the entries
         */

        var SP = this.activity?.getSharedPreferences(getString(R.string.my_set_file),
            AppCompatActivity.MODE_PRIVATE
        )
        var myEdit = SP?.edit()

        // set count to value saved in shared preferences
        count = SP!!.getInt("count", 0)

        _add_binding.btnAdd.setOnClickListener {
            count += 1
            // make a string for the id depending on the value of count
            when (count){
                in 1..9 -> id = "000$count"
                in 10..99-> id = "00$count"
                in 100..999 -> id = "0$count"
                else -> id = "$count"
            }
            // call function to add our data and pass it 'id'
            addDataToFirebase(id)
            // make sure you save the new count to shared preferences
            myEdit?.apply {
                putInt("count", count)
                apply()
            }

            findNavController().navigate(R.id.action_addFragment_to_listFragment)
        }

        _add_binding.btnCancel.setOnClickListener {
            findNavController().navigate(R.id.action_addFragment_to_listFragment)
        }

        return view
    }
    // function adds all of the entered data to firebase using 'id' as the name of the child
    private fun addDataToFirebase(id: String){
        val firstName = _add_binding.etFirstName.text.toString()
        val lastName = _add_binding.etLastName.text.toString()
        val gender = _add_binding.etGender.text.toString()
        val age = _add_binding.etAge.text.toString()
        /* save all data as Person class item, id is defined above and doesn't show in your list, but
        we need it so we know what to reference when we delete an entry
         */

        var user = Person(firstName, lastName, gender, age, id)
        // add the item to firebase db using id as the name of the child
        database.child("users").child(id).setValue(user)


    }


}