package Fragment


import Adapter.Order_history_RecyclerAdapter
import OrderList
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.fooddeliveryapp.R


class OrderHistory : Fragment() {

    lateinit var recyclerView: RecyclerView
    lateinit var recyclerAdapter: Order_history_RecyclerAdapter
    lateinit var layoutManager: RecyclerView.LayoutManager
    lateinit var sharedPreferences: SharedPreferences
    val RestaurantList = arrayListOf<OrderList>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view= inflater.inflate(R.layout.fragment_order_history, container, false)
        sharedPreferences = requireActivity().getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)

        recyclerView= view.findViewById(R.id.recyclerView_history)

        val queue= Volley.newRequestQueue( activity as Context)
        val url="http://13.235.250.119/v2/orders/fetch_result/${sharedPreferences.getString("mobile_number","6455")
            ?.substring(0,4)}  "
        val jsonObjectRequest=object : JsonObjectRequest(Method.GET,url,null, Response.Listener {

            val array=it.getJSONObject("data")
            val success= array.getBoolean("success")
            if(success)
            {
                val data = array.getJSONArray("data")
                for(i in 0 until data.length())
                {
                    val jsonObject= data.getJSONObject(i);
                    val restaurantObject= OrderList(jsonObject.getString("order_id"),jsonObject.getString("restaurant_name"),jsonObject.getString("total_cost"),jsonObject.getString("order_placed_at").substring(0,8),jsonObject.getJSONArray("food_items"))
                    RestaurantList.add(restaurantObject)

                    layoutManager= LinearLayoutManager(activity)
                    recyclerAdapter= Order_history_RecyclerAdapter(activity as Context,RestaurantList)
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