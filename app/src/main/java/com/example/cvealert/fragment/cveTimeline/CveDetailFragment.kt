package com.example.cvealert.fragment.cveTimeline

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.example.cvealert.R
import com.example.cvealert.fragment.subscription.AddOrEditSubscriptionFragmentArgs

class CveDetailFragment : Fragment() {

    private val args by navArgs<AddOrEditSubscriptionFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_cve_detail, container, false)


        // Inflate the layout for this fragment
        return view
    }
}