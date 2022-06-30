package Activity

import Fragment.DashBoardFragement
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.media.session.MediaSessionManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.fooddeliveryapp.R
import com.example.fooddeliveryapp.Welcome_page
import org.json.JSONObject
import java.util.HashMap

class Login_page : AppCompatActivity() {


    lateinit var sharedPreferences: SharedPreferences
    lateinit var MobileNumber : EditText
    lateinit var password : EditText
    lateinit var login: Button
    lateinit var text_forgetPassword : TextView
    lateinit var  text_signup : TextView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferences = getSharedPreferences("MyPreferences" , Context.MODE_PRIVATE )
        val isLoggedIn = sharedPreferences.getBoolean("isLoggedIn",false)

        if(isLoggedIn)
        {
            startActivity(Intent(this, Welcome_page::class.java))
            finish()
        }

        setContentView(R.layout.activity_login_page)


         MobileNumber = findViewById(R.id.editTextPersonNumber)
         password = findViewById(R.id.editTextPassword)
         login = findViewById(R.id.login_button)
         text_forgetPassword = findViewById(R.id.forgetPassword)
         text_signup = findViewById(R.id.signUp)

        text_forgetPassword.setOnClickListener {
            startActivity(Intent(this, Forget_Password_page::class.java))
        }
        text_signup.setOnClickListener {
            startActivity(Intent(this, Registration_page::class.java))
        }


       login.setOnClickListener{

            val etMobileNumber = MobileNumber.text.toString()
            val etPassword = password.text.toString()

            val queue = Volley.newRequestQueue(this@Login_page)
            val url = "http://13.235.250.119/v2/login/fetch_result"
            val jsonParams = JSONObject()
            jsonParams.put("mobile_number", etMobileNumber )
            jsonParams.put("password", etPassword)

            val jsonObjectRequest = object : JsonObjectRequest(Method.POST,url,jsonParams, Response.Listener {

                val data = it.getJSONObject("data")
                val success = data.getBoolean("success")

                if (success) {

                    val response = data.getJSONObject("data")

                    sharedPreferences.edit().putBoolean("isLoggedIn", true).apply()
                    sharedPreferences.edit().putString("name", response.getString("name")).apply()
                    sharedPreferences.edit().putString("mobile_number", response.getString("mobile_number")).apply()
                    sharedPreferences.edit().putString("address", response.getString("address")).apply()
                    sharedPreferences.edit().putString("email", response.getString("email")).apply()


                    startActivity(Intent(this, Welcome_page::class.java))

                } else {
                    Toast.makeText(this, "Error!!", Toast.LENGTH_SHORT).show()
                }


            }, Response.ErrorListener{
                Toast.makeText(this, "Error Response", Toast.LENGTH_SHORT).show()
            })

            {
                override fun getHeaders(): MutableMap<String, String> {
                    val headers = HashMap<String,String>()
                    headers["Content-Type"]="application/json"
                    headers["token"]="cb907b4856fdfe"
                    return headers
                }
            }


           queue.add(jsonObjectRequest)

        }




    }


}