package com.example.apiapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.apiapp.model.UserResponse

class UserAdapter(private val mList: MutableList<UserResponse>) :
    RecyclerView.Adapter<UserAdapter.UserItemViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserItemViewHolder {
       val view = LayoutInflater.from(parent.context).inflate(R.layout.user_item,parent,false)
        return  UserItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mList.size
    }
    override fun onBindViewHolder(holder: UserItemViewHolder, position: Int) {
                val user = mList[position]
                holder.name.text=user.firstname
                holder.DOB.text=user.birthDate
                holder.id.text=user.id.toString()
    }
    class UserItemViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
            val id: TextView = itemView.findViewById(R.id.tvId)
            val name: TextView = itemView.findViewById(R.id.tvName)
            val DOB: TextView = itemView.findViewById(R.id.tvDOB)



    }
}