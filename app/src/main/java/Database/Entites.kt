package Database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Restaurants")
data class Entites(
    @PrimaryKey val Id: String,
    @ColumnInfo(name = "restaurant_name") val Name: String,
    @ColumnInfo(name = "restaurant_rating") val Rating: String,
    @ColumnInfo(name = "restaurant_cost") val Cost_for_one: String,
    @ColumnInfo(name = "restaurant_image") val Image_url: String
    )

