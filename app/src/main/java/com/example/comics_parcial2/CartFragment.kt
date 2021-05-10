package com.example.comics_parcial2

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_cart.*
import kotlinx.android.synthetic.main.fragment_comic_list.*

abstract class SwipeToDelete (context: Context,
                              direccion: Int, direccionArrastre: Int):
    ItemTouchHelper.SimpleCallback(direccion, direccionArrastre){
    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return true
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

    }

}

class CartFragment : Fragment() {

    private lateinit var database: FirebaseDatabase
    private lateinit var reference: DatabaseReference
    private val userCart = ArrayList<Comic>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        database = FirebaseDatabase.getInstance()
        val usuario = Firebase.auth.currentUser
        reference = database.getReference("cart/${usuario.uid}")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cart, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(userCart.size == 0){
            retrieveCartData()
        }
        else{
            recycler_cart.apply{
                layoutManager = LinearLayoutManager(activity)
                adapter = CartAdapter(userCart, context)
            }
        }
    }

    private fun retrieveCartData(){
        recycler_cart.apply {
            reference.addValueEventListener(object: ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    if(userCart.size == 0){
                        for(comic in snapshot.children){
                            var objeto = comic.getValue(Comic::class.java)
                            userCart.add(objeto as Comic)
                        }
                    }
                    //Log.i("ImportantInfo", "State of cart: $userCart")

                    layoutManager = LinearLayoutManager(activity)
                    adapter = CartAdapter(userCart, context)

                    val item = object : SwipeToDelete(context,
                        ItemTouchHelper.UP,ItemTouchHelper.LEFT){
                        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                            super.onSwiped(viewHolder, direction)
                            val comic = userCart[ viewHolder.adapterPosition ]
                            borraComic(comic)

                        }
                    }
                    val itemTouchHelper = ItemTouchHelper(item)
                    itemTouchHelper.attachToRecyclerView(recycler_cart)

                }
                override fun onCancelled(error: DatabaseError) {
                    Log.i("cartError", "Failed to get user comics in cart.")
                }
            })
        }
    }

    private fun borraComic(comic: Comic){
        val usuario= Firebase.auth.currentUser
        val referencia= FirebaseDatabase.getInstance().getReference("cart/${usuario.uid}/${comic.id}")
        userCart.remove(comic)
        referencia.removeValue()
    }

}