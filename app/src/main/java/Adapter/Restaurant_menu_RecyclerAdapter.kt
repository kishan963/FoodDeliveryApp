package Adapter

import android.content.Context
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fooddeliveryapp.R
import Restaurant_menu
import android.graphics.Color
import android.widget.Button
import com.example.fooddeliveryapp.RestaurantMenu


class Restaurant_menu_RecyclerAdapter(val context: Context, val ItemList: ArrayList<Restaurant_menu>) : RecyclerView.Adapter<Restaurant_menu_RecyclerAdapter.Restaurant_menu_ViewHolder>() {
    lateinit var sharedPreferences: SharedPreferences
    lateinit var sharedPreferences1: SharedPreferences
    lateinit var hs : HashSet<String>
    lateinit var hs1 : HashSet<String>

    class Restaurant_menu_ViewHolder(view : View) : RecyclerView.ViewHolder(view)
    {
        val restaurant_item : TextView = view.findViewById(R.id.textView_item)
        val item_price : TextView = view.findViewById(R.id.txtOnePrice_price)
        val add_button : TextView = view.findViewById(R.id.add_button)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Restaurant_menu_ViewHolder {
        hs= HashSet()
        hs1= HashSet()
        val view= LayoutInflater.from(parent.context).inflate(R.layout.recycler_restaurant_menu_single,parent,false)
        return Restaurant_menu_RecyclerAdapter.Restaurant_menu_ViewHolder(view)
    }

    override fun onBindViewHolder(holder: Restaurant_menu_ViewHolder, position: Int) {
        val restaurant = ItemList[position]
        holder.restaurant_item.text=restaurant.Name1
        holder.item_price.text=restaurant.Cost_for_one1
        sharedPreferences = context.getSharedPreferences("foodOrder" , Context.MODE_PRIVATE )
        sharedPreferences1 = context.getSharedPreferences("foodOrder_item" , Context.MODE_PRIVATE )
        holder.add_button.setOnClickListener{

               if( holder.add_button.hint=="1")
               {
                   holder.add_button.setText("Remove")
                   holder.add_button.setBackgroundColor(Color.GRAY)
                   holder.add_button.setHint("2")
                   sharedPreferences.edit().putString("user_id","99").apply()
                   sharedPreferences.edit().putString("restaurant_id",restaurant.Id2 ).apply()

                   hs.add(restaurant.Id1)
                   sharedPreferences.edit().putStringSet("food_id",hs).apply()
                   hs1.add(restaurant.Name1+"#"+restaurant.Cost_for_one1)
                   sharedPreferences1.edit().putStringSet("foodOrder_item",hs1).apply()

               }
            else
               {
                   holder.add_button.setText("Add")
                   holder.add_button.setHint("1")
                   hs.remove(restaurant.Id1)
                   holder.add_button.setBackgroundColor(Color.parseColor("#E6493D"))
                   sharedPreferences.edit().putStringSet("food_id",hs).apply()
                   hs1.remove(restaurant.Name1+"#"+restaurant.Cost_for_one1)
                   sharedPreferences1.edit().putStringSet("foodOrder_item",hs1).apply()

               }


        }


    }

    override fun getItemCount(): Int {
        return ItemList.size
    }

}




