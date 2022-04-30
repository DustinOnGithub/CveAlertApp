package com.example.cvealert.fragment.cveTimeline

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.cvealert.R
import com.example.cvealert.database.cve.Cve
import com.example.cvealert.database.relation.CveWithSubscriptionAndCPEs
import com.example.cvealert.util.Constants

class TimelineAdapter : RecyclerView.Adapter<TimelineAdapter.TimelineViewHolder>() {

    private var cveList = emptyList<CveWithSubscriptionAndCPEs>()
    private lateinit var context: Context

    class TimelineViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimelineViewHolder {

        return TimelineViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.timeline_row, parent, false)
        )
    }

    override fun onBindViewHolder(holder: TimelineViewHolder, position: Int) {

        val item = cveList[position]
        val view = holder.itemView

        var shortDescription = item.cve.description
        val shortDescriptionLength = 40
        if (shortDescription.length > shortDescriptionLength) {
            shortDescription = shortDescription.substring(0, shortDescriptionLength) + "..."
        }

        view.findViewById<TextView>(R.id.cveStringTv).text = item.cve.cve
        view.findViewById<TextView>(R.id.vendorTV).text = item.subscription.vendor
        view.findViewById<TextView>(R.id.productTV).text = item.subscription.product
        view.findViewById<TextView>(R.id.publishedTV).text = item.cve.publishedDate
        view.findViewById<TextView>(R.id.descriptionShortTV).text = shortDescription
        setCVSS(view, item.cve)

        view.setOnClickListener {
            val action =
                TimelineFragmentDirections.actionTimelineFragmentToCveDetailFragment(
                    item
                )
            view.findNavController().navigate(action)
        }
    }

    private fun setCVSS(view: View, cve: Cve) {

        val cvssTypeTv = view.findViewById<TextView>(R.id.cvssTypeTv)
        val severityTv = view.findViewById<TextView>(R.id.severityTv)

        if (cve.cvssV3Score != Constants.NO_CVSS_SCORE) {
            cvssTypeTv.text = "CVSS-v3"
            severityTv.text = cve.cvssV3Score.toString()
            severityTv.setTextColor(
                ContextCompat.getColor(
                    context,
                    when (cve.cvssV3Severity) {
                        "CRITICAL" -> R.color.cvssCritical
                        "HIGH" -> R.color.cvssHigh
                        "MEDIUM" -> R.color.cvssMedium
                        "LOW" -> R.color.cvssLow
                        else -> Color.BLACK
                    }
                )
            )
        } else if (cve.cvssV2Score != Constants.NO_CVSS_SCORE) {
            cvssTypeTv.text = "CVSS-v2"
            severityTv.text = cve.cvssV2Score.toString()
            severityTv.setTextColor(
                ContextCompat.getColor(
                    context,
                    when (cve.cvssV3Severity) {
                        "HIGH" -> R.color.cvssHigh
                        "MEDIUM" -> R.color.cvssMedium
                        "LOW" -> R.color.cvssLow
                        else -> Color.BLACK
                    }
                )
            )
        } else {
            cvssTypeTv.text = ""
            severityTv.text = "N/A"
        }
    }

    override fun getItemCount(): Int {
        return cveList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(
        cveListAndCPEs: List<CveWithSubscriptionAndCPEs>,
        ctx: Context
    ) {
        this.cveList = cveListAndCPEs
        notifyDataSetChanged()
        context = ctx
    }

}