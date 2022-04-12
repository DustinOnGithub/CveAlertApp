package com.example.cvealert

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.findNavController

class SubscriptionsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view: View = inflater.inflate(R.layout.fragment_subscriptions, container, false)
        val addSubscriptionBtn: Button = view.findViewById<Button>(R.id.addSubscriptionButton)

        addSubscriptionBtn.setOnClickListener { myView: View ->
            myView.findNavController()
                .navigate(R.id.action_subscriptionsFragment_to_addSubscriptionFragment)
        }

        return view
    }

}