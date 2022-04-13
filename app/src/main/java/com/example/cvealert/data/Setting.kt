package com.example.cvealert.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Setting")
class Setting(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo(name = "api_ey")
    val apiKey: String? = null
)