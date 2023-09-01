package com.example.recyclerviewdemo

import android.app.LauncherActivity.ListItem
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter

// Step 2 => Set an Adapter for Recycler View (:RecyclerView.Adapter<>())

class MyRvAdapter(val fruitsList :List<Fruit>) : RecyclerView.Adapter<MyViewHolder>() {


    // Step 4 => implement 3 Methods
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val listItem = layoutInflater.inflate(R.layout.list_item,parent,false)

        return MyViewHolder(listItem)
    }

    override fun getItemCount(): Int {
        return fruitsList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val fruits = fruitsList[position]
//        holder.MyTextView.text="Hello From onBindViewHolder $position"
//        holder.MyTextView.text=fruits.name
        holder.bind(fruits)
    }


}

//Step 3 =>Create View Holder Class
class MyViewHolder (val view : View):RecyclerView.ViewHolder(view){

    fun bind(fruit: Fruit) {

        val MyTextView = view.findViewById<TextView>(R.id.tvListItem)
        MyTextView.text=fruit.name

        val suppler=fruit.suppler
        view.setOnClickListener {
            Toast.makeText(view.context, "Suppler is $suppler", Toast.LENGTH_SHORT).show()
        }
    }

}