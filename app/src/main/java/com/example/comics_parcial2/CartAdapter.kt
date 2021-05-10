package com.example.comics_parcial2

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class CartAdapter (private val comics : List<Comic>, val context: Context)
    : RecyclerView.Adapter<CartAdapter.ComicViewHolder>(){

    class ComicViewHolder(val row: View) : RecyclerView.ViewHolder(row){

        fun bind(property: Comic){
            val title = row.findViewById<TextView>(R.id.row_title)
            val description = row.findViewById<TextView>(R.id.row_plot)
            val cost = row.findViewById<TextView>(R.id.row_cost)
            val image = row.findViewById<ImageView>(R.id.row_image)

            title.text = property.title
            description.text = property.description
            cost.text = "$" + property.cost.toString()

            Glide.with(row.context)
                .load(property.picture)
                .fitCenter()
                .into(image)
        }
    }

    //Crea el renglón
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComicViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val renglon_vista = inflater.inflate(R.layout.recycler_row,parent, false)
        return ComicViewHolder(renglon_vista)
    }

    override fun getItemCount(): Int {
        return comics!!.size
    }

    override fun onBindViewHolder(holder: ComicViewHolder, position: Int) {
        val comic = comics[position]
        holder.bind(comics!![position])
    }
}