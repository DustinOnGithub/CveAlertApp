package com.example.cvealert.data.Cve

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cve")
data class Cve(
    @PrimaryKey
    var cve: String,
    var description: String,
    //todo: create a cpe-table for cpe relation: one-to-N
    var cvssV2String: String,
    var cvssV3String: String,
    var cvssV2Score: Float,
    var cvssV3Score: Float,
    var cvssV2Severity: String,
    var cvssV3Severity: String,
    var publishedDate: String,
    var lastModifiedDate: String
)
