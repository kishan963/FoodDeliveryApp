package com.example.fooddeliveryapp

import Activity.Cart
import Adapter.Cart_RecyclerAdapter
import Adapter.DashboardRecyclerAdapter
import Adapter.Restaurant_menu_RecyclerAdapter
import Database.Entites
import Fragment.DashBoardFragement
import Restaurant_menu
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import java.util.HashMap

class RestaurantMenu : AppCompatActivity() {

    lateinit var recyclerView: RecyclerView
    lateinit var recyclerAdapter: Restaurant_menu_RecyclerAdapter
    lateinit var layoutManager: RecyclerView.LayoutManager

    lateinit var goToCart : Button
    lateinit var toolbar: androidx.appcompat.widget.Toolbar
    val ItemList = arrayListOf<Restaurant_menu>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurant_menu)

        recyclerView= findViewById(R.id.recyclerView1)
        toolbar=findViewById(R.id.toolbar_layout1)
        goToCart=findViewById(R.id.goToCart)
         val adder = intent.getStringExtra("ID")
        setSupportActionBar(toolbar)
        supportActionBar?.title = intent.getStringExtra("title")

        goToCart.setOnClickListener{

            startActivity(Intent(this, Cart::class.java))

        }

        val queue= Volley.newRequestQueue(this)
        val url="http://13.235.250.119/v2/restaurants/fetch_result/+$adder"
        val jsonObjectRequest=object : JsonObjectRequest(
            Method.GET,url,null, Response.Listener {

            val array=it.getJSONObject("data")
            val success= array.getBoolean("success")
            if(success)
            {
                val data = array.getJSONArray("data")

                for(i in 0 until data.length())
                {
                    val jsonObject= data.getJSONObject(i);
                    val restaurantObject= Restaurant_menu(jsonObject.getString("id"),jsonObject.getString("name"),jsonObject.getString("cost_for_one"),jsonObject.getString("restaurant_id"))
                    ItemList.add(restaurantObject)

                    layoutManager= LinearLayoutManager(this)
                    recyclerAdapter= Restaurant_menu_RecyclerAdapter(this  ,ItemList)
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

    }

}