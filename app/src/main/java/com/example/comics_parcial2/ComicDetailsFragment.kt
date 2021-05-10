package com.example.comics_parcial2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_comic_details.*
import kotlinx.android.synthetic.main.fragment_login.*


class ComicDetailsFragment : Fragment() {
    private val args by navArgs<ComicDetailsFragmentArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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


    }

}