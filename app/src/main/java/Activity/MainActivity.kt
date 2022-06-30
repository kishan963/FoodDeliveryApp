package Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import com.example.fooddeliveryapp.Forget_Page2
import com.example.fooddeliveryapp.R
import com.example.fooddeliveryapp.Welcome_page

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
       // supportActionBar!!.hide()

        val handler = Handler()
        handler.postDelayed(Runnable {
          val intent = Intent(this , Login_page :: class.java )
          startActivity(intent)
        }, 2000)

    }
}