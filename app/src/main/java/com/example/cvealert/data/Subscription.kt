package com.example.cvealert.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey


enum class Part {
    UNDEFINED,
    APPLICATIONS,
    HARDWARE,
    OPERATING_SYSTEM
}

@Entity(tableName = "subscription")
class Subscription(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val vendor: String,
    val product: String,
    @ColumnInfo(name = "push_up_notification")
    val pushUpNotification: Boolean,
    val part: Part,
    val version: String,
    val update: String,
    @ColumnInfo(name = "is_active")
    val isActive: Boolean,
    val edition: String,
    val language: String,
    @ColumnInfo(name = "sw_edition")
    val swEdition: String,
    @ColumnInfo(name = "target_software")
    val targetSoftware: String,
    @ColumnInfo(name = "target_hardware")
    val targetHardware: String,
    val other: String
)