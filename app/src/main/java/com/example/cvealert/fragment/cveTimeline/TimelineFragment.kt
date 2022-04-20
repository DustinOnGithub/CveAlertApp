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

        val repository = Repository()
        val mainViewModelFactory = MainViewModelFactory(repository)
        mainViewModel = ViewModelProvider(this, mainViewModelFactory)[MainViewModel::class.java]
        mainViewModel.getCves(
            resultsPerPage = 1,
            apiKey = null,
            cpeMatchString = null,
            pubStartDate = null,
            pubEndDate = null,
            startIndex = null
        )
        mainViewModel.cvesResponse.observe(viewLifecycleOwner, Observer { response ->
            if (response.isSuccessful) {
                Log.v("Response", response.body()?.resultsPerPage.toString())
                Log.v("Response", response.body()?.startIndex.toString())
                Log.v("Response", response.body()?.totalResults.toString())
                val temp = response.body()?.result
                Log.v("Response", response.body()?.result.toString())
            } else {
                Log.v("Response", response.errorBody().toString())
                Log.v("Response", response.code().toString())
            }
        })

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_timeline, container, false)
    }
}