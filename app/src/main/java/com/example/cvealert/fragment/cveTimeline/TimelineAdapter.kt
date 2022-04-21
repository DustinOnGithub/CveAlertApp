package com.example.cvealert.fragment.cveTimeline

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cvealert.R
import com.example.cvealert.database.cve.Cve

class TimelineAdapter : RecyclerView.Adapter<TimelineAdapter.TimelineViewHolder>() {

    private var cveList = emptyList<Cve>()

    class TimelineViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimelineViewHolder {

        return TimelineViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.timeline_row, parent, false)
        )
    }

    override fun onBindViewHolder(holder: TimelineViewHolder, position: Int) {

        val currentCVE = cveList[position]

        holder.itemView.findViewById<TextView>(R.id.cveStringTv).text = currentCVE.cve
        holder.itemView.findViewById<TextView>(R.id.severityTv).text =
            currentCVE.cvssV3Score.toString()
    }

    override fun getItemCount(): Int {
        return cveList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(
        cveList: List<Cve>,
    ) {
        this.cveList = cveList
        notifyDataSetChanged()
    }

}