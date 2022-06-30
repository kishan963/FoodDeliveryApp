package com.example.fooddeliveryapp

import Activity.Login_page
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class Forget_Page2 : AppCompatActivity() {

     lateinit var etOTP : EditText
   lateinit var etPass : EditText
   lateinit var etREpass : EditText
   lateinit var submit : Button
   lateinit var etMobile : String
   lateinit var etmail : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forget_page2)

           etOTP = findViewById(R.id.editTextOTP)
        etPass = findViewById(R.id.editTextNewPassword)
        etREpass = findViewById(R.id.editTextReEnterNewPassword)
        submit = findViewById(R.id.Submit_button)

        if(intent!=null)
        {
            etMobile = intent.getStringExtra("mobile_number").toString()
            etmail = intent.getStringExtra("email").toString()
        }


          submit.setOnClickListener{

              val queue = Volley.newRequestQueue(this)
              val url = "http://13.235.250.119/v2/reset_password/fetch_result"
              val jsonParams = JSONObject()
              jsonParams.put("mobile_number", etMobile )
              jsonParams.put("password", etPass)
              jsonParams.put("otp",etOTP.text.toString())


              val jsonObjectRequest = object : JsonObjectRequest(Method.POST,url,jsonParams, Response.Listener {

                  val data = it.getJSONObject("data")
                  val success = data.getBoolean("success")

                  if (success) {

                      startActivity(Intent(this, Login_page ::class.java))

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