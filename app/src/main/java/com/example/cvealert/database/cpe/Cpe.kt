package com.example.cvealert.database.cpe

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey


//todo: add a foreign key
@Entity(
    tableName = "cpe",
    indices = [Index(value = ["string", "vulnerable", "cve"], unique = true)]
)
data class Cpe(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var string: String,
    var vulnerable: Boolean,
    var cve: String
)
