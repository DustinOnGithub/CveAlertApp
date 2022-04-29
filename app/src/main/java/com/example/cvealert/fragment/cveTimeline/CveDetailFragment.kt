package com.example.cvealert.fragment.cveTimeline

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat
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
        val cve = args.selectedCVE.cve
        val subscription = args.selectedCVE.subscription

        view.findViewById<TextView>(R.id.titleTv).text = cve.cve
        view.findViewById<TextView>(R.id.cvssV2Tv).text = cve.cvssV2Score.toString()
        view.findViewById<TextView>(R.id.cvssV3TV).text = cve.cvssV3Score.toString()
        view.findViewById<TextView>(R.id.cvssV2StringTv).text = cve.cvssV2String
        view.findViewById<TextView>(R.id.cvssV3String).text = cve.cvssV3String
        view.findViewById<TextView>(R.id.cveDescriptionTV).text = cve.description
        view.findViewById<TextView>(R.id.publishedDateTv).text = cve.publishedDate
        view.findViewById<TextView>(R.id.lastModifiedTv).text = cve.lastModifiedDate
        view.findViewById<TextView>(R.id.subscriptionStringTv).text =
            Cpe.generateStringFromSubscription(subscription)
        view.findViewById<Button>(R.id.linkToNvdBt).text = "Link to NVD"
        view.findViewById<TextView>(R.id.cveDetailVendorTv).text = subscription.vendor
        view.findViewById<TextView>(R.id.cveDetailproductTv).text = subscription.product

        view.findViewById<TextView>(R.id.cvssV3TV).setTextColor(
            ContextCompat.getColor(
                requireContext(),
                when (cve.cvssV3Severity) {
                    "CRITICAL" -> R.color.cvssCritical
                    "HIGH" -> R.color.cvssHigh
                    "MEDIUM" -> R.color.cvssMedium
                    "LOW" -> R.color.cvssLow
                    else -> Color.BLACK
                }
            )
        )

        view.findViewById<TextView>(R.id.cvssV2Tv).setTextColor(
            ContextCompat.getColor(
                requireContext(),
                when (cve.cvssV3Severity) {
                    "HIGH" -> R.color.cvssHigh
                    "MEDIUM" -> R.color.cvssMedium
                    "LOW" -> R.color.cvssLow
                    else -> Color.BLACK
                }
            )
        )

        return view
    }
}