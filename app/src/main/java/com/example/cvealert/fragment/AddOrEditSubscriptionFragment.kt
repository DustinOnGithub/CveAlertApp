package com.example.cvealert.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import com.example.cvealert.R
import com.example.cvealert.data.MyViewModel
import com.example.cvealert.data.Subscription

class AddOrEditSubscriptionFragment : Fragment() {

    private lateinit var myViewModel: MyViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add_or_edit_subscription, container, false)
        val saveSubscriptionBtn = view.findViewById<Button>(R.id.saveSubscriptionBtn)
        myViewModel = ViewModelProvider(this).get(MyViewModel::class.java)

        saveSubscriptionBtn.text = resources.getString(R.string.add)
        saveSubscriptionBtn.setOnClickListener {
            saveSubscription(view)
        }

        return view
    }

    private fun saveSubscription(view: View) {

        //todo: add update functionality

        val hardOrSoftware = view.findViewById<RadioGroup>(R.id.HardOrSoftwareRG)
        val vendorTv = view.findViewById<TextView>(R.id.vendorTV)
        val productTv = view.findViewById<TextView>(R.id.productTV)
        val pushUpNotificationCb = view.findViewById<CheckBox>(R.id.pushUpCB)

        myViewModel.insertSubscription(
            Subscription(
                id = 0,
                vendor = vendorTv.text.toString(),
                product = productTv.text.toString(),
                pushUpNotification = pushUpNotificationCb.isChecked
            )
        )

        clearView(view)

        Toast.makeText(requireContext(), "Subscription added!", Toast.LENGTH_SHORT).show()
    }

    private fun clearView(view: View) {
        view.findViewById<RadioGroup>(R.id.HardOrSoftwareRG).check(R.id.SoftwareRB)
        view.findViewById<TextView>(R.id.vendorTV).text = ""
        view.findViewById<TextView>(R.id.productTV).text = ""
        view.findViewById<CheckBox>(R.id.pushUpCB).isChecked = true
    }
}