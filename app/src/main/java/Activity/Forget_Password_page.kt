package Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.fooddeliveryapp.Forget_Page2
import com.example.fooddeliveryapp.R
import com.example.fooddeliveryapp.Welcome_page
import org.json.JSONObject
import java.util.HashMap

class Forget_Password_page : AppCompatActivity() {

    lateinit var etMobileNumber_forget : EditText
    lateinit var etEmailAddress_forget : EditText
    lateinit var Next : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forget_password_page)

        etMobileNumber_forget = findViewById(R.id.editTextMobileNumber_forget)
        etEmailAddress_forget = findViewById(R.id.editTextEmailAddress_forget)
        Next =findViewById(R.id.Next_button_forget)

        Next.setOnClickListener{

            val etMobileNumber = etMobileNumber_forget.text.toString()
            val etEmail = etEmailAddress_forget.text.toString()

            val queue = Volley.newRequestQueue(this)
            val url = "http://13.235.250.119/v2/forgot_password/fetch_result"
            val jsonParams = JSONObject()
            jsonParams.put("mobile_number", etMobileNumber )
            jsonParams.put("email", etEmail)

            val jsonObjectRequest = object : JsonObjectRequest(Method.POST,url,jsonParams, Response.Listener {

                val data = it.getJSONObject("data")
                val success = data.getBoolean("success")

                if (success) {

                   val intent = Intent(this, Forget_Page2::class.java)
                    Toast.makeText(this, "YEH SUCCESS", Toast.LENGTH_SHORT).show()
                    intent.putExtra("mobile_number",etMobileNumber);
                    intent.putExtra("email",etEmail)
                    startActivity(intent)


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