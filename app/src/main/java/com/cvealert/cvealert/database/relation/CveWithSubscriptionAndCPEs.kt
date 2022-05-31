package com.cvealert.cvealert.database.relation

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Relation
import com.cvealert.cvealert.database.cpe.Cpe
import com.cvealert.cvealert.database.cve.Cve
import com.cvealert.cvealert.database.subscription.Subscription
import kotlinx.parcelize.Parcelize

@Parcelize
data class CveWithSubscriptionAndCPEs(
    @Embedded val cve: Cve,
    @Relation(
        entity = Subscription::class,
        parentColumn = "subscription_id",
        entityColumn = "id"
    )
    val subscription: Subscription,

    @Relation(
        entity = Cpe::class,
        parentColumn = "cve",
        entityColumn = "cve"
    )
    val CPEs: List<Cpe>

) : Parcelable
