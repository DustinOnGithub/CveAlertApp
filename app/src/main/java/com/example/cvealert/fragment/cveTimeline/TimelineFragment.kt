package com.example.cvealert.fragment.cveTimeline

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cvealert.R
import com.example.cvealert.database.MyViewModelDb

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
            adapter.setData(cves, requireContext())
        }

        return view
    }
}