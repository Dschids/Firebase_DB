package com.example.firebasedb

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.firebasedb.databinding.CustomRowBinding
import com.google.firebase.Firebase
import com.google.firebase.database.database


class ListAdapter(var userList: ArrayList<Person>):
                    RecyclerView.Adapter<ListAdapter.MyViewHolder>() {
    class MyViewHolder(val _binding: CustomRowBinding): RecyclerView.ViewHolder(_binding.root){
        // function that sets all the text in our list item
        fun bind(ourItem: Person){
            _binding.tvGender.text = ourItem.gender.toString()
            _binding.tvFirstName.text = ourItem.firstName.toString()
            _binding.tvLastName.text = ourItem.lastName.toString()
            _binding.tvAge.text = ourItem.age.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemBinding = CustomRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(itemBinding)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem: Person = userList[position]
        holder.bind(currentItem)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    fun deleteItem(adapterPosition: Int) {
        /* create a reference to the firebase data we want, using the id from the userList in the swiped
        position we remove the child with that id
         */
        val dbref = Firebase.database.getReference("/users").child(userList[adapterPosition].id.toString())
        val rmtask = dbref.removeValue()

        }

}