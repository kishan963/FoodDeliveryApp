package Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fooddeliveryapp.R

class Cart_RecyclerAdapter(val context: Context ,val ItemList: ArrayList<String> ) : RecyclerView.Adapter<Cart_RecyclerAdapter.Cart_ViewHolder>()
{
    class Cart_ViewHolder(view : View) : RecyclerView.ViewHolder(view)
    {
        val restaurant_item : TextView = view.findViewById(R.id.textView_item_cart)
        val item_price : TextView = view.findViewById(R.id.txtOnePrice_price_cart)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Cart_ViewHolder {

        val view= LayoutInflater.from(parent.context).inflate(R.layout.cart_recyclerview_single,parent,false)
        return Cart_RecyclerAdapter.Cart_ViewHolder(view)
    }

    override fun onBindViewHolder(holder: Cart_ViewHolder, position: Int) {
        val item = ItemList[position]
        var st = arrayOf(item.split("#") )
        holder.restaurant_item.text= st[0][0].toString()
        holder.item_price.text="Rs. "+st[0][1].toString()






    }

    override fun getItemCount(): Int {
        return ItemList.size
    }
}