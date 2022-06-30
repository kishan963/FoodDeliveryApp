package Fragment

import Adapter.DashboardRecyclerAdapter
import Database.Entites
import Database.RestaurantDatabase
import Restaurant
import android.app.Application
import android.content.Context
import android.content.Entity
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
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.fooddeliveryapp.R
import kotlin.collections.HashMap


class DashBoardFragement : Fragment() {

 lateinit var recyclerView: RecyclerView
 lateinit var layoutManager: RecyclerView.LayoutManager
 val RestaurantList = arrayListOf<Restaurant>()
    lateinit var recyclerAdapter : DashboardRecyclerAdapter
    lateinit var progressBar: ProgressBar
    lateinit var progressLayout : RelativeLayout
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view= inflater.inflate(R.layout.fragment_dash_board_fragement, container, false)
        recyclerView=view.findViewById(R.id.recyclerView)
        progressLayout=view.findViewById(R.id.RelativeProgressBar)
        progressBar=view.findViewById(R.id.ProgressBar)
        progressLayout.visibility= View.VISIBLE


        val queue= Volley.newRequestQueue( activity as Context)
        val url="http://13.235.250.119/v2/restaurants/fetch_result/"
        val jsonObjectRequest=object : JsonObjectRequest(Method.GET,url,null, Response.Listener {

             val array=it.getJSONObject("data")
           val success= array.getBoolean("success")
            if(success)
            {
                val data = array.getJSONArray("data")
                for(i in 0 until data.length())
                {
                     val jsonObject= data.getJSONObject(i);
                    val restaurantObject= Restaurant(jsonObject.getString("id"),jsonObject.getString("name"),jsonObject.getString("rating"),jsonObject.getString("cost_for_one"),jsonObject.getString("image_url"))
                    RestaurantList.add(restaurantObject)
                    progressLayout.visibility=View.GONE
                    layoutManager=LinearLayoutManager(activity)
                    recyclerAdapter= DashboardRecyclerAdapter(activity as Context,RestaurantList)
                    recyclerView.adapter=recyclerAdapter
                    recyclerView.layoutManager=layoutManager



                }



            }
        },
        Response.ErrorListener {
            println("Error is ##")
        })
        {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String,String>()
                headers["Content-Type"]="application/json"
                headers["token"]="cb907b4856fdfe"
                return headers
            }
        }
         queue.add(jsonObjectRequest)
        return view
    }



}
