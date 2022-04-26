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
import com.example.cvealert.Cpe
import com.example.cvealert.database.MyViewModelDb
import com.example.cvealert.database.subscription.Subscription
import com.example.cvealert.util.Constants

class SubscriptionAdapter : RecyclerView.Adapter<SubscriptionAdapter.SubscriptionViewHolder>() {

    private var subscriptionList = emptyList<Subscription>()
    private lateinit var myViewModelDb: MyViewModelDb
    private lateinit var subscriptionFragmentContext: Context

    class SubscriptionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val deleteButton: ImageButton = itemView.findViewById(R.id.deleteSubscriptionIB)

        fun addDeleteSubscriptionListener(
            item: Subscription,
            myViewModelDb: MyViewModelDb,
            context: Context
        ) {
            deleteButton.setOnClickListener {
                val builder = AlertDialog.Builder(context)
                builder.setPositiveButton("Yes") { _, _ ->
                    myViewModelDb.deleteSubscription(item)
                    myViewModelDb.deleteCveWherePublishedDateAfterSync(Constants.getLastCVEDateTime())
                }
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubscriptionViewHolder {

        return SubscriptionViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.subscription_row, parent, false)
        )
    }

    override fun onBindViewHolder(holder: SubscriptionViewHolder, position: Int) {

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

        holder.addDeleteSubscriptionListener(
            currentItem,
            myViewModelDb,
            subscriptionFragmentContext
        )
    }

    override fun getItemCount(): Int {
        return subscriptionList.size
    }

    fun setData(
        subscriptionList: List<Subscription>,
        viewModelDb: MyViewModelDb,
        context: Context
    ) {
        myViewModelDb = viewModelDb
        this.subscriptionFragmentContext = context
        this.subscriptionList = subscriptionList
        notifyDataSetChanged()
    }


}