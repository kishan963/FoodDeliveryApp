package Activity

import Fragment.DashBoardFragement
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.fooddeliveryapp.R
import org.json.JSONObject
import java.util.*

class Registration_page : AppCompatActivity() {

    lateinit var etName : EditText
    lateinit var etEmail : EditText
    lateinit var  etMobileNumber : EditText
    lateinit var etDeliveryAderess : EditText
    lateinit var etChoosePassword : EditText
    lateinit var etReEnterPassword : EditText
    lateinit var register : Button



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration_page)

        etName = findViewById(R.id.editTextEnterYourName)
        etEmail = findViewById(R.id.editTextEmailAdress)
        etMobileNumber = findViewById(R.id.editTextMobileNumber)
        etDeliveryAderess= findViewById(R.id.editTextDeliveryAddress)
        etChoosePassword = findViewById(R.id.editTextChoosePassword)
        etReEnterPassword = findViewById(R.id.editTextReenterPassword)
        register= findViewById(R.id.Register_button)


        register.setOnClickListener{

            val queue = Volley.newRequestQueue( this@Registration_page )
            val url = "http://13.235.250.119/v2/register/fetch_result"

            val jsonParams = JSONObject()
            jsonParams.put("name", etName.text.toString() )
            jsonParams.put("mobile_number", etMobileNumber.text.toString() )
            jsonParams.put("password", etChoosePassword.text.toString() )
            jsonParams.put("address", etDeliveryAderess.text.toString() )
            jsonParams.put("email", etEmail.text.toString() )
            Toast.makeText(this, "Register", Toast.LENGTH_SHORT).show()
            val jsonObjectRequest = object : JsonObjectRequest(Method.POST,url,jsonParams,Response.Listener {

                val data = it.getJSONObject("data")
                val success = data.getBoolean("success")
                Toast.makeText(this, "success", Toast.LENGTH_SHORT).show()
                if (success) {

                    startActivity(Intent(this, Login_page::class.java))
                    finish()
                } else {
                    Toast.makeText(this, "Error!!", Toast.LENGTH_SHORT).show()
                }


            },Response.ErrorListener{

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