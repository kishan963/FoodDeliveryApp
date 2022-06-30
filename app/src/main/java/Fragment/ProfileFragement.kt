package Fragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.example.fooddeliveryapp.R


class ProfileFragement : Fragment() {
lateinit var sharedPreferences: SharedPreferences
lateinit var  etName : EditText
lateinit var etMobileNumber : EditText
lateinit var  etEmail : EditText
lateinit var  etAddress : EditText

    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_profile_fragement, container, false)
        sharedPreferences = requireActivity().getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)

        etName = view.findViewById(R.id.editTextPersonName_profile)
        etAddress=view.findViewById(R.id.editTextAddress_profile)
        etEmail =view.findViewById(R.id.editTextEmail_profile)
        etMobileNumber=view.findViewById(R.id.editTextMobileNumber_profile)


        etName.setText(sharedPreferences.getString("name","foodPe")).toString()
        etMobileNumber.setText(sharedPreferences.getString("mobile_number","foodPe")).toString()
        etEmail.setText(sharedPreferences.getString("email","foodPe")).toString()
        etAddress.setText(sharedPreferences.getString("address","foodPe")).toString()





        return view
    }


}