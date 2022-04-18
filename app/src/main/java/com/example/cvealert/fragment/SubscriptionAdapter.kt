package com.example.cvealert.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.cvealert.R
import com.example.cvealert.data.Cpe
import com.example.cvealert.data.Subscription

class SubscriptionAdapter : RecyclerView.Adapter<SubscriptionAdapter.MyViewHolder>() {

    private var subscriptionList = emptyList<Subscription>()

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.subscription_row, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val currentItem = subscriptionList[position]

        holder.itemView.findViewById<ImageButton>(R.id.editSubscriptionIB).setOnClickListener {
            val action =
                SubscriptionsFragmentDirections.actionSubscriptionsFragmentToAddSubscriptionFragment(
                    currentItem
                )
            holder.itemView.findNavController().navigate(action)
        }

        holder.itemView.findViewById<TextView>(R.id.cpeStringRowTV).text =
            Cpe.generateStringFromSubscription(currentItem)
    }

    override fun getItemCount(): Int {
        return subscriptionList.size
    }

    fun setData(subscriptionList: List<Subscription>) {
        this.subscriptionList = subscriptionList
        notifyDataSetChanged()
    }


}