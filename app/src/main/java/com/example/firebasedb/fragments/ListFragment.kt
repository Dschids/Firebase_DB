package com.example.firebasedb.fragments

import android.content.ClipData.Item
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
    private var users = ArrayList<Person>()
    lateinit var thisAdapter: ListAdapter

    val changeListener: ValueEventListener = object: ValueEventListener{
        override fun onDataChange(snapshot: DataSnapshot) {
            if (snapshot.hasChildren()){
                var count = snapshot.childrenCount
                users.clear()
                for (child in snapshot.children){
                    val holddata = child.getValue(Person::class.java)
                    users.add(holddata!!)
                }
                /* need to have this so list shows up when you go to the list fragment without
                making changes
                 */
                thisAdapter.notifyDataSetChanged()
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

        database = Firebase.database.getReference("/users")
        database.addValueEventListener(changeListener)

        // Inflate the layout for this fragment
        _list_binding = FragmentListBinding.inflate(inflater, container, false)
        val view = _list_binding.root

        _list_binding.fabAdd.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_addFragment)
        }


        // create a recyclerview variable for ease of use (don't have to keep typing _binding.rvlist)
        val recyclerView = _list_binding.rvList
        recyclerView.layoutManager = LinearLayoutManager(requireContext())


        thisAdapter = ListAdapter(users)
        recyclerView.adapter = thisAdapter

        /* itemTouchHelper defines what happens on a swipe or click and drag, Not doing anything with drag so that is 0
        and we're setting up swipe left
         */

        val itemTouchHelper = ItemTouchHelper(object: ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }
            // what happens on swipe, don't have to worry about direction because we only have 1
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                /* reference a function in ListAdapter called delete item, and pass it the swiped item's position
                so it knows what to delete
                 */
                thisAdapter.deleteItem(viewHolder.adapterPosition)

            }
        })
        // attach the touchHelper to our recycler view, if you don't do this swiping does nothing
        itemTouchHelper.attachToRecyclerView(_list_binding.rvList)

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        database.removeEventListener(changeListener)
    }

}