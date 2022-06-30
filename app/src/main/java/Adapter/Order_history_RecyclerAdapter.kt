package Adapter

import OrderList
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class Order_history_RecyclerAdapter(val context: Context, val ItemList: ArrayList<OrderList>) : RecyclerView.Adapter<Order_history_RecyclerAdapter.Order_history_ViewHolder>()  {

    class Order_history_ViewHolder(view : View) : RecyclerView.ViewHolder(view)
    {
        val restaurant_name : TextView = view.findViewById(com.example.fooddeliveryapp.R.id.textView_history_single)
        val order_time : TextView = view.findViewById(com.example.fooddeliveryapp.R.id.textView2_history_single)
        val listView : ListView = view.findViewById(com.example.fooddeliveryapp.R.id.ListView_history_single)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Order_history_ViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(com.example.fooddeliveryapp.R.layout.orderhistory_single,parent,false)
        return Order_history_RecyclerAdapter.Order_history_ViewHolder(view)
    }

    override fun onBindViewHolder(holder: Order_history_ViewHolder, position: Int) {
        val llist = ItemList[position]
     //   holder.restaurant_item.text=restaura.Name1
        holder.restaurant_name.text=llist.Name_history
        holder.order_time.text=llist.order_placed_time_history

        val arr = ArrayList<String>()

        for( i in 1..llist.food_item_history.length()  )
        {
            val ss= llist.food_item_history[i-1].toString().split(",")[1].split(":")[1]
            arr.add(ss);
        }

     //   val arrayAdapter = ArrayAdapter(context, com.example.fooddeliveryapp.R.layout.listview_layout, arr)

       // println(arr)
        //holder.listView.adapter =  arrayAdapter

    }

    override fun getItemCount(): Int {
        return ItemList.size
    }

}