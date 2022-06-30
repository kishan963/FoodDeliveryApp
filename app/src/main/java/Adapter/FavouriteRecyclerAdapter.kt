package Adapter

import Database.Entites
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.fooddeliveryapp.R
import com.example.fooddeliveryapp.RestaurantMenu
import com.squareup.picasso.Picasso

class FavouriteRecyclerAdapter(val context: Context, private val RestaurantList: ArrayList<Entites>) : RecyclerView.Adapter<FavouriteRecyclerAdapter.FavouriteViewHolder>() {

    class FavouriteViewHolder(view : View) : RecyclerView.ViewHolder(view)
    {
        val ReataurantName : TextView = view.findViewById(R.id.textViewRecyclerView)
        val imageOfRes : ImageView = view.findViewById(R.id.imgRestaurantImage)
        val rating : TextView = view.findViewById(R.id.txtRestaurantRating)
        val OnePrice : TextView = view.findViewById(R.id.txtOnePrice)
        val layout_single : CardView = view.findViewById(R.id.layout_single)
        val fav_image : ImageView = view.findViewById(R.id.imageView_fav_image)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.recycler_dashboard_single,parent,false)
        return FavouriteRecyclerAdapter.FavouriteViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavouriteViewHolder, position: Int) {
        val restaurant = RestaurantList[position]
        holder.ReataurantName.text=restaurant.Name

        Picasso.get().load(restaurant.Image_url).error(R.drawable.foodpe_logo).into(holder.imageOfRes)
        holder.rating.text=restaurant.Rating
        holder.OnePrice.text="₹"+restaurant.Cost_for_one+"/Person"
        holder.fav_image.setImageResource(R.drawable.ic_favourites)
        holder.layout_single.setOnClickListener {

            val intent = Intent(context, RestaurantMenu::class.java)
            intent.putExtra("ID",restaurant.Id)
            intent.putExtra("title",restaurant.Name)
            context.startActivity(intent)

        }

    }

    override fun getItemCount(): Int {
        return RestaurantList.size
    }

}