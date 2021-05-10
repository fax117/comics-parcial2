package com.example.comics_parcial2

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.fragment_comic_list.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import org.json.JSONException
import java.math.BigInteger
import java.security.MessageDigest
import java.time.Instant
import java.time.format.DateTimeFormatter
import kotlin.random.Random

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ComicListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ComicListFragment : Fragment() {

    private val comicList = ArrayList<Comic>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_comic_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(comicList.size == 0){
            apiCall()
        }
        else{
            recycler_catalogue.apply{
                layoutManager = LinearLayoutManager(activity)
                adapter = ComicsAdapter(comicList, context)
            }
        }
        //Línea nueva cuidar merge con Fabricio
        listToCartButton.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_comicListFragment_to_cartFragment))
    }

/*    fun createData(): ArrayList<Comic>{
        val comics = ArrayList<Comic>()
        comics.add(Comic("Spider-man","1965", "https://image.api.playstation.com/vulcan/img/rnd/202011/0714/vuF88yWPSnDfmFJVTyNJpVwW.png", Random.nextInt(100, 1000)))
        comics.add(Comic("Venom","1965", "https://i.pinimg.com/originals/f4/ae/59/f4ae591ab43e08db1a1d4afdc27969f0.jpg", Random.nextInt(100,1000)))
        comics.add(Comic("Iron-man","1965", "https://www.tonica.la/__export/1599939641284/sites/debate/img/2020/09/12/iron-man-fortnite.jpg_423682103.jpg", Random.nextInt(100,1000)))
        return apiCall()
    }*/

    fun md5(input: String): String {
        val md = MessageDigest.getInstance("MD5")
        return BigInteger(1, md.digest(input.toByteArray())).toString(16).padStart(32, '0')
    }

    fun apiCall() = GlobalScope.async{
        val queue = Volley.newRequestQueue(activity)
        val timestamp = DateTimeFormatter.ISO_INSTANT.format(Instant.now())
        val pubKey = "538f0ae87260c9fe2a09c1cd3adf8c82"
        val priKey = "2798764a702f9afb98e31311f1eb8aee2d4e80d1"
        val hash =md5(timestamp.toString() + priKey + pubKey)

        val url = "https://gateway.marvel.com:443/v1/public/comics?format=comic&formatType=comic&noVariants=true&limit=10&ts="+timestamp.toString()+"&apikey="+pubKey+"&hash="+hash

        Log.i("API", url)

        val request = GlobalScope.async{
            JsonObjectRequest(
                Request.Method.GET, url, null,
                { response ->
                    try {
                        val data = response.getJSONObject("data")
                        val results = data.getJSONArray("results")
                        for (i in 0 until results.length()) {
                            val comic = results.getJSONObject(i)
                            val id = ""
                            val title = comic.getString("title")
                            val description = comic.getString("description")
                            val imageArr = comic.getJSONArray("images")
                            var path = ""
                            val cost = 2.99
                            for (j in 0 until imageArr.length()) {
                                val image = imageArr.getJSONObject(j)
                                path = image.getString("path") + "/detail.jpg"

                                val builder = StringBuilder(path)
                                builder.insert(4, "s")
                                path = builder.toString()

                                Log.i("image", "Yo wtf is this: $i - ${path.toString()}")
                            }
                            val newComic = Comic(id, title, description, path, cost)
                            if(newComic.description != "null"){
                                //Filter to add only comics with available description in database
                                comicList.add(newComic)
                            }
                            //Log.i("name", comicList.toString())
                        }
                        recycler_catalogue.apply{
                            layoutManager = LinearLayoutManager(activity)
                            adapter = ComicsAdapter(comicList, context)
                        }
                    } catch (e: JSONException) {
                        e.printStackTrace();
                    }
                },
                { error ->
                    error.printStackTrace();
                    Log.i("error", error.toString())
                }
        )
        }.await()
        queue.add(request);
    }

}