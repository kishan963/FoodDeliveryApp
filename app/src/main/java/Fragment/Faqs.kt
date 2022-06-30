package Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.fooddeliveryapp.R


class Faqs : Fragment() {

  lateinit var text : TextView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_faqs, container, false)

        text= view.findViewById(R.id.text4)
        text.setText("What is foodPe Customer Care Number?\n" +
                "We value our customer’s time and hence moved away from a single customer care number to a comprehensive chat-based support system for quick and easy resolution.\n" +
                "You no longer have to go through the maze of an IVRS call support.\n" +
                "Just search for your issue in the help section on this page and initiate a chat with us.\n" +
                "A customer care executive will be assigned to you shortly. You can also email us your issue on support@foodPe.in\n" +
                "\n" +
                "Note: We value your privacy and your information is safe with us. Please do not reveal any personal information, bank account number, OTP etc.\n" +
                "to another person. \n" +
                "A foodPe representative will never ask you for these details.\n" +
                "Please do not reveal these details to fraudsters and imposters claiming to be calling on our behalf.\n" +
                "Be vigilant and do not entertain phishing calls or emails.\n" +
                "\nCan I edit my order?\n" +
                "Your order can be edited before it reaches the restaurant. You could contact customer support team via chat or call to do so. Once order is placed and restaurant starts preparing your food, you may not edit its contents").toString()

        return view
    }


}