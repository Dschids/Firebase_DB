package com.example.firebasedb

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.firebasedb.databinding.CustomRowBinding


class ListAdapter: RecyclerView.Adapter<ListAdapter.MyViewHolder>() {

    private var userList = ArrayList<Person>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemBinding = CustomRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(itemBinding)


    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = userList[position]
        holder.bind(currentItem)
    }

    override fun getItemCount(): Int {
        return userList.size
    }
    class MyViewHolder(val _binding: CustomRowBinding): RecyclerView.ViewHolder(_binding.root){
        fun bind(ourItem: Person){
            _binding.tvGender.text = ourItem.gender.toString()
            _binding.tvFirstName.text = ourItem.firstName
            _binding.tvLastName.text = ourItem.lastName
            _binding.tvAge.text = ourItem.age.toString()
        }
    }

    fun setData (user: ArrayList<Person>){
        this.userList = user
        notifyDataSetChanged()
    }
}