package Activity

import Fragment.DashBoardFragement
import Fragment.OrderHistory
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.fooddeliveryapp.R
import com.example.fooddeliveryapp.Welcome_page
import java.util.*

class Order_placed : AppCompatActivity() {

    lateinit var Ok : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_placed)

        Ok = findViewById(R.id.button_ok)

        Ok.setOnClickListener{

            startActivity(Intent(this, Welcome_page::class.java))
             finish()
        }

    }
}