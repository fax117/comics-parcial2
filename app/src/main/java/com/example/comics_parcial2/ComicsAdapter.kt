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

class ComicsAdapter (private val animes : List<Comic>, val context: Context)
    : RecyclerView.Adapter<ComicsAdapter.ComicViewHolder>(){

    inner class ComicViewHolder(row: View) : RecyclerView.ViewHolder(row){
        var titulo = row.findViewById<TextView>(R.id.titulo)
        var anio = row.findViewById<TextView>(R.id.anio)
        var plot = row.findViewById<TextView>(R.id.plot)
        var foto = row.findViewById<ImageView>(R.id.foto)
    }

    //Crea el renglón
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimeViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val renglon_vista = inflater.inflate(R.layout.animes_renglon,parent, false)
        return AnimeViewHolder(renglon_vista)
    }

    //Asocia datos con los elementos del renglón
    override fun onBindViewHolder(holder: AnimeViewHolder, position: Int) {
        val anime = animes[position]
        holder.foto.setImageResource(anime.picture)
        holder.titulo.text = anime.titulo
        holder.anio.text = anime.anio
        holder.plot.text = anime.plot
        holder.tipo.text = anime.tipo
        holder.rank.text = anime.rank.toString()

        holder.itemView.setOnClickListener {
            var toast = Toast.makeText(context, "El rank de ${anime.titulo} es: ${anime.rank.toString()}", Toast.LENGTH_LONG)
            toast.show()
            val action = AnimesFragmentDirections.actionAnimesFragmentToAnimeFragment(anime)
            holder.itemView.findNavController().navigate(action)

        }

    }

    // Cuantos elementos tiene la lista
    override fun getItemCount(): Int {
        return animes.size
    }
}