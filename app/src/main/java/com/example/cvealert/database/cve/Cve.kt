package com.example.cvealert.database.cve

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.cvealert.database.subscription.Subscription

@Entity(
    tableName = "cve",
    indices = [Index(value = ["subscription_id"], unique = false)],
    foreignKeys = [ForeignKey(
        entity = Subscription::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("subscription_id"),
        onDelete = ForeignKey.CASCADE
    )]
)
data class Cve(
    @PrimaryKey
    var cve: String,
    var description: String,
    var cvssV2String: String,
    var cvssV3String: String,
    var cvssV2Score: Float,
    var cvssV3Score: Float,
    var cvssV2Severity: String,
    var cvssV3Severity: String,
    var publishedDate: String,
    var lastModifiedDate: String,
    var subscription_id: Int
)
