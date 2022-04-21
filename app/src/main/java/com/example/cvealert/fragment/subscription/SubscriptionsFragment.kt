package com.example.cvealert.fragment.subscription

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cvealert.R
import com.example.cvealert.database.MyViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class SubscriptionsFragment : Fragment() {

    private lateinit var myViewModel: MyViewModel

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

        myViewModel = ViewModelProvider(this).get(MyViewModel::class.java)
        myViewModel.getAllSubscriptions.observe(viewLifecycleOwner, Observer { subscriptions ->
            adapter.setData(subscriptions, myViewModel, requireContext())
        })

        view.findViewById<FloatingActionButton>(R.id.addSubscriptionButton)
            .setOnClickListener { thisView: View ->
                thisView.findNavController()
                    .navigate(R.id.action_subscriptionsFragment_to_addSubscriptionFragment)
            }

        return view
    }

}