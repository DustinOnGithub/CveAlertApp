package com.example.cvealert.fragment.subscription

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.cvealert.R
import com.example.cvealert.data.Cpe
import com.example.cvealert.data.MyViewModel
import com.example.cvealert.data.Subscription

class SubscriptionAdapter : RecyclerView.Adapter<SubscriptionAdapter.MyViewHolder>() {

    private var subscriptionList = emptyList<Subscription>()
    private lateinit var myViewModel: MyViewModel
    private lateinit var subscriptionFragmentContext: Context

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val deleteButton: ImageButton = itemView.findViewById(R.id.deleteSubscriptionIB)

        fun addDeleteSubscriptionListener(
            item: Subscription,
            myViewModel: MyViewModel,
            context: Context
        ) {
            deleteButton.setOnClickListener {
                val builder = AlertDialog.Builder(context)
                builder.setPositiveButton("Yes") { _, _ -> myViewModel.deleteSubscription(item) }
                builder.setNegativeButton("No") { _, _ -> }
                builder.setTitle("Delete subscription")
                builder.setMessage(
                    "Are you sure you want to delete subscription for \n${
                        Cpe.generateStringFromSubscription(item)
                    }\n?"
                )
                builder.create().show()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.subscription_row, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val currentItem = subscriptionList[position]
        val isActiveIV = holder.itemView.findViewById<ImageView>(R.id.subIsActiveIv)

        holder.itemView.findViewById<ImageButton>(R.id.editSubscriptionIB).setOnClickListener {
            val action =
                SubscriptionsFragmentDirections.actionSubscriptionsFragmentToAddSubscriptionFragment(
                    currentItem
                )
            holder.itemView.findNavController().navigate(action)
        }

        holder.itemView.findViewById<TextView>(R.id.cpeStringRowTV).text =
            Cpe.generateStringFromSubscription(currentItem)

        if (currentItem.isActive) {
            isActiveIV.setImageResource(R.drawable.ic_baseline_check_24)
            isActiveIV.visibility = View.VISIBLE
        } else {
            isActiveIV.visibility = View.GONE
        }

        holder.itemView.findViewById<ImageView>(R.id.subNotificationEnabledIv).setImageResource(
            if (currentItem.pushUpNotification)
                R.drawable.ic_baseline_notifications_24
            else
                R.drawable.ic_baseline_notifications_off_24
        )

        holder.addDeleteSubscriptionListener(currentItem, myViewModel, subscriptionFragmentContext)
    }

    override fun getItemCount(): Int {
        return subscriptionList.size
    }

    fun setData(subscriptionList: List<Subscription>, viewModel: MyViewModel, context: Context) {
        myViewModel = viewModel
        this.subscriptionFragmentContext = context
        this.subscriptionList = subscriptionList
        notifyDataSetChanged()
    }


}