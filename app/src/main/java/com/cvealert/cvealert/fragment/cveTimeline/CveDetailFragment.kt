package com.cvealert.cvealert.fragment.cveTimeline

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.cvealert.cvealert.R
import com.cvealert.cvealert.database.cpe.Cpe


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
        view.findViewById<TextView>(R.id.subscriptionStringTv).text = subscription.getCPE23URL()

        fillCPElist(view, args.selectedCVE.CPEs)

        view.findViewById<Button>(R.id.linkToNvdBt).setOnClickListener {
            val viewIntent = Intent(
                "android.intent.action.VIEW",
                Uri.parse("https://nvd.nist.gov/vuln/detail/${cve.cve}")
            )
            startActivity(viewIntent)
        }


        view.findViewById<TextView>(R.id.cvssV3TV).setTextColor(
            ContextCompat.getColor(
                requireContext(),
                when (cve.cvssV3Severity) {
                    "CRITICAL" -> R.color.cvssCritical
                    "HIGH" -> R.color.cvssHigh
                    "MEDIUM" -> R.color.cvssMedium
                    else -> R.color.cvssLow
                }
            )
        )

        view.findViewById<TextView>(R.id.cvssV2Tv).setTextColor(
            ContextCompat.getColor(
                requireContext(),
                when (cve.cvssV2Severity) {
                    "HIGH" -> R.color.cvssHigh
                    "MEDIUM" -> R.color.cvssMedium
                    else -> R.color.cvssLow
                }
            )
        )

        return view
    }

    private fun fillCPElist(view: View, cpes: List<Cpe>) {

        var text = ""
        var vulnerable = ""
        var runningOn = ""

        cpes.forEach { cpe ->

            if (cpe.vulnerable) {
                vulnerable += "\n\t" + cpe.string
            } else {
                runningOn += "\n\t" + cpe.string
            }
        }

        text += "Vulnerable:$vulnerable"
        if (runningOn.isNotEmpty()) {
            text += "\nRunning on/with: $runningOn"
        }

        view.findViewById<TextView>(R.id.cpeListTv).text = text
    }
}