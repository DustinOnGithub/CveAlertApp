package com.example.cvealert.fragment.subscription

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cvealert.R
import com.example.cvealert.database.MyViewModelDb
import com.google.android.material.floatingactionbutton.FloatingActionButton

class SubscriptionsFragment : Fragment() {

    private lateinit var myViewModelDb: MyViewModelDb

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view: View = inflater.inflate(R.layout.fragment_subscriptions, container, false)
        val recyclerView = view.findViewById<RecyclerView>(R.id.subscriptionRecyclerView)
        val adapter = SubscriptionAdapter()

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        myViewModelDb = ViewModelProvider(this)[MyViewModelDb::class.java]
        myViewModelDb.getAllSubscriptions.observe(viewLifecycleOwner) { subscriptions ->
            if (subscriptions.isEmpty()) {
                view.findViewById<TextView>(R.id.noSubscriptionsAvailableTV).visibility =
                    View.VISIBLE
            } else {
                view.findViewById<TextView>(R.id.noSubscriptionsAvailableTV).visibility =
                    View.INVISIBLE
            }

            adapter.setData(subscriptions, myViewModelDb, requireContext())
        }

        view.findViewById<FloatingActionButton>(R.id.addSubscriptionButton)
            .setOnClickListener { thisView: View ->
                thisView.findNavController()
                    .navigate(R.id.action_subscriptionsFragment_to_addSubscriptionFragment)
            }

        return view
    }

}