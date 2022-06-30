package Database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface RestaurantDao {

    @Insert
    fun insertRestaurant(entites: Entites)
    @Delete
    fun deleteRestaurant(entites: Entites)
    @Query("SELECT * FROM Restaurants")
    fun getAllRestaurant() : List<Entites>
    @Query("SELECT * FROM Restaurants WHERE Id = :id")
    fun getRestaurantById(id : String ) : Entites

}