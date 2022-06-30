package Fragment

import Adapter.FavouriteRecyclerAdapter
import Database.Entites
import Database.RestaurantDatabase
import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.RelativeLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.fooddeliveryapp.R


class FavouriteFragement : Fragment() {
    lateinit var recyclerView: RecyclerView
    lateinit var layoutManager: RecyclerView.LayoutManager
    var RestaurantList = arrayListOf<Entites>()
    lateinit var recyclerAdapter : FavouriteRecyclerAdapter
    lateinit var progressBar: ProgressBar
    lateinit var progressLayout : RelativeLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_favourite_fragement, container, false)

        recyclerView=view.findViewById(R.id.recyclerView_fav)
        progressLayout=view.findViewById(R.id.RelativeProgressBar_fav)
        progressBar=view.findViewById(R.id.ProgressBar_fav)
        progressLayout.visibility= View.VISIBLE
        RestaurantList = retriveFavourites(activity as Context ).execute().get() as ArrayList<Entites>

        if(activity!=null)
        {
            progressLayout.visibility=View.GONE
            layoutManager= LinearLayoutManager(activity)
            recyclerAdapter=FavouriteRecyclerAdapter(activity as Context, RestaurantList)
            recyclerView.adapter=recyclerAdapter
            recyclerView.layoutManager=layoutManager

        }

        return view
    }

    class retriveFavourites(val context: Context ) : AsyncTask<Void,Void,List<Entites>>() {
        override fun doInBackground(vararg params: Void?): List<Entites> {
           val db = Room.databaseBuilder(context,RestaurantDatabase :: class.java,"restaurant-db").build()
            return db.RestaurantDao().getAllRestaurant()
        }

    }


}

