package com.example.cvealert.fragment.cveTimeline

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.cvealert.Cpe
import com.example.cvealert.R
import com.example.cvealert.database.relation.CveWithSubscription
import com.example.cvealert.fragment.subscription.SubscriptionsFragmentDirections
import org.w3c.dom.Text

class TimelineAdapter : RecyclerView.Adapter<TimelineAdapter.TimelineViewHolder>() {

    private var cveList = emptyList<CveWithSubscription>()

    class TimelineViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimelineViewHolder {

        return TimelineViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.timeline_row, parent, false)
        )
    }

    override fun onBindViewHolder(holder: TimelineViewHolder, position: Int) {

        val item = cveList[position]

        holder.itemView.findViewById<TextView>(R.id.cveStringTv).text = item.cve.cve
        holder.itemView.findViewById<TextView>(R.id.severityTv).text =
            item.cve.cvssV3Score.toString()
        holder.itemView.findViewById<TextView>(R.id.cpeString).text =
            Cpe.generateStringFromSubscription(item.subscription)

        holder.itemView.setOnClickListener {
            val action =
                TimelineFragmentDirections.actionTimelineFragmentToCveDetailFragment(
                    item
                )
            holder.itemView.findNavController().navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return cveList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(
        cveList: List<CveWithSubscription>,
    ) {
        this.cveList = cveList
        notifyDataSetChanged()
    }

}