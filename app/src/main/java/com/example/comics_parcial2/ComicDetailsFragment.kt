package com.example.comics_parcial2

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import android.widget.Toast.LENGTH_SHORT
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_comic_details.*
import kotlinx.android.synthetic.main.fragment_comic_list.*
import kotlinx.android.synthetic.main.fragment_login.*


class ComicDetailsFragment : Fragment() {

    private lateinit var database: FirebaseDatabase
    private lateinit var reference: DatabaseReference
    private val args by navArgs<ComicDetailsFragmentArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        database = FirebaseDatabase.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_comic_details, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //details_image.setImageResource(args.comic.picture)
        details_title.text = args.comic.title
        details_cost.text = "$ ${args.comic.cost.toString()}"
        details_description.text = args.comic.description

        Glide.with(view?.context!!)
            .load(args.comic.picture)
            .fitCenter()
            .into(details_image)

        setupButtons()
    }

    private fun setupButtons(){
        button.setOnClickListener{
            addToCart()
            Toast.makeText(context, "${args.comic.title} añadido al carrito correctamente.", LENGTH_SHORT).show()
            button.findNavController().navigate(R.id.action_comicDetailsFragment_to_comicListFragment)
        }
    }

    private fun addToCart(){
        val title = args.comic.title
        val description = args.comic.description
        val picture = args.comic.picture
        val cost = args.comic.cost

        val usuario = Firebase.auth.currentUser

        reference = database.getReference("cart/${usuario.uid}")

        val id = reference.push().key
        val comic = Comic(
            id.toString(), title, description, picture, cost
        )
        reference.child(id!!).setValue(comic)
    }

}