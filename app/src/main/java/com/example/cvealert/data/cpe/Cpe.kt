package com.example.cvealert.data.cpe

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey


@Entity(tableName = "cpe")
data class Cpe(
    @PrimaryKey
    var id: Int,
    var string: String,
    var vulnerable: Boolean,
    var cve: String
)
