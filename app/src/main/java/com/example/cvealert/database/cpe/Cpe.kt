package com.example.cvealert.database.cpe

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "cpe")
data class Cpe(
    @PrimaryKey
    var id: Int,
    var string: String,
    var vulnerable: Boolean,
    var cve: String
)
