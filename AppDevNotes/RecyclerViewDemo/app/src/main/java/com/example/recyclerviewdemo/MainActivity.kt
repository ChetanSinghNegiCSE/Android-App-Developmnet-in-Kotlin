package com.example.recyclerviewdemo

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
/* Step 1 => Add Layout Manager to recyclerView
   Step 2 => Create  an Adapter  class for Recycler View (:RecyclerView.Adapter<>())
   Step 3 => Create View Holder Class ((val view : View):RecyclerView.ViewHolder(view))
   Step 4 => implement 3 Methods
   step 5 => Create List Item (.xml)
   step 6 => Add LayoutInflater for List item
            {val layoutInflater = LayoutInflater.from(parent.context)
            val listItem = layoutInflater.inflate(R.layout.list_item,parent,false)}
               step 11 => Define a list of Items
   step 7 =>In onCreateViewHolder return MyViewHolder(listItem)
   step 8 => Define a list of Items
   Step 9 => Set Adapter to Recycler View in Main Activity and Pass the List as Actual Argumnat
   step 10 =>Return the size of The list in getItemCount fun
   step 11 => Get The View Components in ViewHolder Class
   step 12 =>Initialize View Components   with list

-------------------------------------------------------------------------------------------------------
                            {Send a list of Objects to the Adapter}

   step 8 => Create A Data Class(No Need Getter and Setter )
             ex-:
               data class Fruit(val name : String , val suppler : String)

   Step 9 => Create a List of Data class Objects in Main Activity
               Ex:-
                    val fruitsList = listOf<Fruit>(
                    Fruit("Apple","Joe"),
                    Fruit ("Grapes","Frank"))
    //Add Click Listener To item
    step 13 => view.setOnClickListener {} "1st  option for item clicked in a Recycler View"

*/
class MainActivity : AppCompatActivity() {
    val fruitsList = listOf<Fruit>(
        Fruit("Apple","Joe"),
        Fruit ("Grapes","Frank"),
        Fruit("Peach","Tom"),
        Fruit("Mango","Alex"),
        Fruit("Lemon","Jerry"),
        Fruit("Orange","Alexa"),
        Fruit("Banana","Franklin")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

       /* Step 1 =>

            3 types of Layout Managers
                1-> Linear Layout Manager for    - Standard List Views
                2-> Grid Layout Manager  for     - Display items in a Grid
                3-> Staggered Layout Manager for - position list item in a Staggered

                    or we can make our Custom layout Managers
        */

        val recyclerView = findViewById<RecyclerView>(R.id.rvMy)
        recyclerView.layoutManager= LinearLayoutManager(this)
        recyclerView.setBackgroundColor(Color.BLUE)

        // Step 8 => Set an Adapter for Recycler View

        recyclerView.adapter=MyRvAdapter(fruitsList)

}
}