package Activity

import Adapter.Cart_RecyclerAdapter
import Adapter.Restaurant_menu_RecyclerAdapter
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.fooddeliveryapp.R
import com.example.fooddeliveryapp.Welcome_page
import org.json.JSONArray
import org.json.JSONObject
import java.util.HashMap
import kotlin.collections.HashSet


class Cart : AppCompatActivity() {


    lateinit var sharedPreferences: SharedPreferences
    lateinit var sharedPreferences1: SharedPreferences
    lateinit var sharedPreferences2: SharedPreferences
    lateinit var food_item : ArrayList<String>
    lateinit var food_item_id : ArrayList<String>
    lateinit var recyclerView1: RecyclerView
    lateinit var recyclerAdapter1: Cart_RecyclerAdapter
    lateinit var layoutManager1: RecyclerView.LayoutManager
    lateinit var toolbar1: androidx.appcompat.widget.Toolbar
    lateinit var goToCart : Button
    lateinit var headerText : TextView

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)
        sharedPreferences = getSharedPreferences("foodOrder" , Context.MODE_PRIVATE )
        sharedPreferences1 = getSharedPreferences("foodOrder_item" , Context.MODE_PRIVATE )
        sharedPreferences2 = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)
        food_item=ArrayList<String>()
        food_item_id=ArrayList<String>()
        val set: HashSet<String> = sharedPreferences1.getStringSet("foodOrder_item", HashSet<String>()) as HashSet<String>
        food_item.addAll(set)

        recyclerView1= findViewById(R.id.recyclerView_cart)
        toolbar1=findViewById(R.id.toolbar_layout_cart)
        goToCart= findViewById(R.id.goToProceed)
        headerText =findViewById(R.id.textView_cart)

        setSupportActionBar(toolbar1)
        supportActionBar?.title = "My Cart"
        headerText.setText("Ordering from: "+ sharedPreferences.getString("restaurant_name","").toString() )

        layoutManager1= LinearLayoutManager(this)
        recyclerAdapter1= Cart_RecyclerAdapter(this  ,food_item)
        recyclerView1.adapter=recyclerAdapter1
        recyclerView1.layoutManager=layoutManager1

        val userId = sharedPreferences.getString("user_id","99")
        val resId = sharedPreferences.getString("restaurant_id","01")

        val set1: HashSet<String> = sharedPreferences.getStringSet("food_id", HashSet<String>()) as HashSet<String>
        food_item_id.addAll(set1)

        var sum=0;
        for( i in 1..food_item.size )
        {
            val item = food_item[i-1]
            var st = arrayOf(item.split("#") )
            sum+=Integer.valueOf(st[0][1])
        }

        goToCart.setText("Place Order(Total: Rs. $sum)")

   goToCart.setOnClickListener {


       val jsonArr = JSONArray()

       for (i in 1..food_item_id.size) {
           val objj = JSONObject()
           objj.put("food_item_id", food_item_id[i - 1])
           jsonArr.put(i-1,objj)
       }



       val queue = Volley.newRequestQueue(this)
       val url = "http://13.235.250.119/v2/place_order/fetch_result/"
       val jsonParams = JSONObject()
       jsonParams.put("user_id", sharedPreferences2.getString("mobile_number","1234")
           ?.substring(0,4)
           .toString() )
       if (resId != null) {
           jsonParams.put("restaurant_id", resId.substring(1).toString() )
       }
       jsonParams.put("total_cost", sum.toString())
       jsonParams.put("food", jsonArr)

       val jsonObjectRequest1 = object : JsonObjectRequest(Method.POST, url, jsonParams, Response.Listener {

               val array = it.getJSONObject("data")
               val success1 = array.getBoolean("success")
           System.out.println(it)
               if (success1) {
                   startActivity(Intent(this, Order_placed::class.java))
               }
               else
                   Toast.makeText(this, jsonArr[0].toString() , Toast.LENGTH_SHORT).show()
           },
               Response.ErrorListener {
                   println("No Response")
               }) {
               override fun getHeaders(): MutableMap<String, String> {
                   val headers = HashMap<String, String>()
                   headers["Content-Type"] = "application/json"
                   headers["token"] = "cb907b4856fdfe"
                   return headers
               }
           }

       queue.add(jsonObjectRequest1)


   }
    }
}