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
    //todo: add active field
    //todo: add is_software field
    //todo: add version field
    //todo: add other fields like version, software type
)