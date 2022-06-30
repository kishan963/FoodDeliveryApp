import org.json.JSONArray

data class OrderList (

    val Id_history: String,
    val Name_history: String,
    val total_cost_history_: String,
    val order_placed_time_history: String,
    val food_item_history: JSONArray = TODO()

)