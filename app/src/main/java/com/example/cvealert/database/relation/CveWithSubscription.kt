package com.example.cvealert.database.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.example.cvealert.database.cve.Cve
import com.example.cvealert.database.subscription.Subscription

data class CveWithSubscription(
    @Embedded val cve: Cve,
    @Relation(
        parentColumn = "subscription_id",
        entityColumn = "id"
    )

    val subscription: Subscription
)
