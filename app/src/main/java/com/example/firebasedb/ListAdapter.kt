package com.example.firebasedb

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.firebasedb.databinding.CustomRowBinding


class ListAdapter (var ctx: Context, var ourResource: Int,
                       var items: ArrayList<Person>): ArrayAdapter<Person>(ctx, ourResource, items){

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val layoutInflater = LayoutInflater.from(ctx)
        val view = layoutInflater.inflate(ourResource, null)

        // go to layout and get links to widgets, use view we just created
        val firstName = view.findViewById<TextView>(R.id.tvFirstName)
        val lastName = view.findViewById<TextView>(R.id.tvLastName)
        val gender = view.findViewById<TextView>(R.id.tvGender)
        val age = view.findViewById<TextView>(R.id.tvAge)

        // asign data
        firstName.text = items[position].firstName
        lastName.text = items[position].lastName
        gender.text = items[position].gender
        age.text = items[position].age

        return view
    }

}