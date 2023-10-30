package com.example.restapi.ui.home

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.restapi.Model.PostModel
import com.example.restapi.R

class HomeAdapter(mainActivity: Context, val data: List<PostModel>) :
    RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {
    private val TAG = "RVlog"
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        return HomeViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.home_rv_item_view, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val item = data.get(position)
        holder.title.text = item.title.toString()
        Log.d(TAG, "onBindViewHolder: ${item.title}")
        holder.body.text = item.content.toString()
    }

    class HomeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var title: TextView = itemView.findViewById(R.id.tv_home_item_title)
        var body: TextView = itemView.findViewById(R.id.tv_home_item_body)
    }
}