package com.example.cvealert.fragment.cveTimeline

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cvealert.R
import com.example.cvealert.api.MainViewModel
import com.example.cvealert.api.MainViewModelFactory
import com.example.cvealert.api.Repository
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

        myViewModelDb = ViewModelProvider(this).get(MyViewModelDb::class.java)
        myViewModelDb.getAllCves.observe(viewLifecycleOwner, { cves ->
            adapter.setData(cves)
        })

        //todo: update timeline on swipe down
        //todo: ini timeline with recylerview

        // Inflate the layout for this fragment
        return view
    }
}