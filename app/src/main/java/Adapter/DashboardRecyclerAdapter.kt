package Adapter

import Activity.Login_page
import Activity.Registration_page
import Database.Entites
import Database.RestaurantDatabase
import Fragment.OrderHistory
import Restaurant
import android.content.Context
import android.content.SharedPreferences
import android.media.Image
import android.os.AsyncTask
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.bumptech.glide.Glide
import com.example.fooddeliveryapp.R
import com.example.fooddeliveryapp.RestaurantMenu
import com.example.fooddeliveryapp.Welcome_page
import com.squareup.picasso.Picasso
import android.content.Intent as Intent

class DashboardRecyclerAdapter(val context: Context, private val RestaurantList: ArrayList<Restaurant>) : RecyclerView.Adapter<DashboardRecyclerAdapter.DashBoardViewHolder>() {
    lateinit var sharedPreferences: SharedPreferences
    class DashBoardViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ReataurantName: TextView = view.findViewById(R.id.textViewRecyclerView)
        val imageOfRes: ImageView = view.findViewById(R.id.imgRestaurantImage)
        val rating: TextView = view.findViewById(R.id.txtRestaurantRating)
        val OnePrice: TextView = view.findViewById(R.id.txtOnePrice)
        val layout_single: CardView = view.findViewById(R.id.layout_single)
        val fav_image : ImageView = view.findViewById(R.id.imageView_fav_image)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DashBoardViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_dashboard_single, parent, false)
        return DashBoardViewHolder(view)
    }

    override fun onBindViewHolder(holder: DashBoardViewHolder, position: Int) {
        val restaurant = RestaurantList[position]
        holder.ReataurantName.text = restaurant.Name

        Picasso.get().load(restaurant.Image_url).error(R.drawable.foodpe_logo)
            .into(holder.imageOfRes)
        holder.rating.text = restaurant.Rating
        holder.OnePrice.text = "₹" + restaurant.Cost_for_one + "/Person"


        val listOfFavourites = retriveFavourites(context).execute().get()
        if(listOfFavourites.isNotEmpty() && listOfFavourites.contains(restaurant.Id) )
        {
            holder.fav_image.setImageResource(R.drawable.ic_favourites)
        }
        else
            holder.fav_image.setImageResource(R.drawable.ic_fav)

            holder.fav_image.setOnClickListener {


                val restaurantEntity = Entites(

                    restaurant.Id,
                    restaurant.Name,
                    restaurant.Rating,
                    restaurant.Cost_for_one,
                    restaurant.Image_url

                )
            if (!DBAsyncTask(context, restaurantEntity, 1).execute().get()) {
                val async =
                    DBAsyncTask(context, restaurantEntity, 2).execute()
                val result = async.get()
                if (result) {
                    holder.fav_image.setImageResource(R.drawable.ic_favourites)
                }
            } else {
                val async = DBAsyncTask(context, restaurantEntity, 3).execute()
                val result = async.get()

                if (result) {
                    holder.fav_image.setImageResource(R.drawable.ic_fav)
                }
            }
        }



        holder.layout_single.setOnClickListener {

            com.example.fooddeliveryapp.sharedPreferences = context.getSharedPreferences("foodOrder" , Context.MODE_PRIVATE )
            com.example.fooddeliveryapp.sharedPreferences.edit().putString("restaurant_name",restaurant.Name).apply()

            val intent = Intent(context, RestaurantMenu::class.java)
            intent.putExtra("ID", restaurant.Id)
            intent.putExtra("title", restaurant.Name)
            context.startActivity(intent)

        }


        /*      val entites=Entites(restaurantObject.Id.toString(),restaurantObject.Name.toString(),restaurantObject.Rating.toString(),restaurantObject.Cost_for_one.toString(),restaurantObject.Image_url.toString() )



       */
    }
    override fun getItemCount(): Int {
        return RestaurantList.size
    }

}



class DBAsyncTask(val context: Context, val entites: Entites, val mode : Int ) : AsyncTask<Void, Void, Boolean>(){

        val db= Room.databaseBuilder(context , RestaurantDatabase :: class.java,  "restaurant-db" ).build()
        override fun doInBackground(vararg params: Void?): Boolean {
            when(mode)
            {
                1 -> {
                    val restaurants : Entites? = db.RestaurantDao().getRestaurantById(entites.Id.toString())
                    db.close()
                    return restaurants!=null
                }

                2-> {
                    db.RestaurantDao().insertRestaurant(entites)
                    db.close()
                    return true

                }

                3 -> {
                    db.RestaurantDao().deleteRestaurant(entites)
                    db.close()
                    return true

                }


            }



            return false
        }

    }

class retriveFavourites(val context: Context ) : AsyncTask<Void,Void,List<String>>() {
    override fun doInBackground(vararg params: Void?): List<String> {
        val db = Room.databaseBuilder(context,RestaurantDatabase :: class.java,"restaurant-db").build()
        val list = db.RestaurantDao().getAllRestaurant()

        val listOfIds = arrayListOf<String>()
        for (i in list) {
            listOfIds.add(i.Id.toString())
        }
        return listOfIds
    }

}

