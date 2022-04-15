package com.example.cvealert.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "subscription")
class Subscription(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val vendor: String,
    val product: String,
    @ColumnInfo(name = "push_up_notification")
    val pushUpNotification: Boolean
)