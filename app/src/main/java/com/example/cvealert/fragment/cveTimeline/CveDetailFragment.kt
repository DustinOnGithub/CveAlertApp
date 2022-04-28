package com.example.cvealert.fragment.cveTimeline

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.fragment.navArgs
import com.example.cvealert.Cpe
import com.example.cvealert.R

class CveDetailFragment : Fragment() {

    private val args by navArgs<CveDetailFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_cve_detail, container, false)

        view.findViewById<TextView>(R.id.titleTv).text = args.selectedCVE.cve.cve
        view.findViewById<TextView>(R.id.cvssV2Tv).text =
            args.selectedCVE.cve.cvssV2Score.toString()
        view.findViewById<TextView>(R.id.cvssV3TV).text =
            args.selectedCVE.cve.cvssV3Score.toString()
        view.findViewById<TextView>(R.id.cvssV2StringTv).text = args.selectedCVE.cve.cvssV2String
        view.findViewById<TextView>(R.id.cvssV3String).text = args.selectedCVE.cve.cvssV3String
        view.findViewById<TextView>(R.id.cveDescriptionTV).text = args.selectedCVE.cve.description
        view.findViewById<TextView>(R.id.publishedDateTv).text = args.selectedCVE.cve.publishedDate
        view.findViewById<TextView>(R.id.lastModifiedTv).text =
            args.selectedCVE.cve.lastModifiedDate
        view.findViewById<TextView>(R.id.subscriptionStringTv).text =
            Cpe.generateStringFromSubscription(args.selectedCVE.subscription)
        view.findViewById<Button>(R.id.linkToNvdBt).text = "Link to NVD"



        return view
    }
}