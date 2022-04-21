package com.example.cvealert.fragment.cveTimeline

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.cvealert.R
import com.example.cvealert.api.MainViewModel
import com.example.cvealert.api.MainViewModelFactory
import com.example.cvealert.api.Repository

class TimelineFragment : Fragment() {

    private lateinit var mainViewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //todo: update timeline on swipe down
        //todo: ini timeline with recylerview

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_timeline, container, false)
    }
}