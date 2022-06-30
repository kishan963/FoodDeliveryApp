package Database

import androidx.room.Database
import androidx.room.RoomDatabase
@Database(entities = [Entites :: class], version = 1)
abstract class RestaurantDatabase : RoomDatabase() {
    abstract fun RestaurantDao() : RestaurantDao

}