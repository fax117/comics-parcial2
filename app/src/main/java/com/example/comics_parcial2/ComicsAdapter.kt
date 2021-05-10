package com.example.comics_parcial2

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class ComicsAdapter (private val comics : List<Comic>, val context: Context)
    : RecyclerView.Adapter<ComicsAdapter.ComicViewHolder>(){

    class ComicViewHolder(val row: View) : RecyclerView.ViewHolder(row){

        fun bind(property: Comic){
            var title = row.findViewById<TextView>(R.id.row_title)
            var description = row.findViewById<TextView>(R.id.row_plot)
            var cost = row.findViewById<TextView>(R.id.row_cost)
            var image = row.findViewById<ImageView>(R.id.row_image)

            title.text = property.title
            description.text = property.description
            cost.text = "$ ${property.cost.toString()}"

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

        holder.itemView.setOnClickListener {
            val action = ComicListFragmentDirections.actionComicListFragmentToComicDetailsFragment(comic)
            holder.itemView.findNavController().navigate(action)

        }
    }
}