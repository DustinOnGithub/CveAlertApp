package com.cvealert.cvealert.fragment.cveTimeline

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cvealert.cvealert.R
import com.cvealert.cvealert.database.MyViewModelDb

class TimelineFragment : Fragment() {

    private lateinit var myViewModelDb: MyViewModelDb

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_timeline, container, false)
        val recyclerView = view.findViewById<RecyclerView>(R.id.timelineRecyclerView)
        val adapter = TimelineAdapter()

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        myViewModelDb = ViewModelProvider(this)[MyViewModelDb::class.java]
        myViewModelDb.selectCVEwithSubscriptionAndCPEs.observe(viewLifecycleOwner) { cves ->

            if (cves.isEmpty()) {
                view.findViewById<TextView>(R.id.noDataAvailableTV).visibility = View.VISIBLE
            } else {
                view.findViewById<TextView>(R.id.noDataAvailableTV).visibility = View.INVISIBLE
            }

            adapter.setData(cves, requireContext())
        }

        return view
    }
}