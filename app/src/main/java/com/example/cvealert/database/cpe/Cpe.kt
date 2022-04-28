package com.example.cvealert.database.cpe

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.cvealert.database.cve.Cve


//todo: add a foreign key
@Entity(
    tableName = "cpe",
    indices = [
        Index(value = ["string", "vulnerable", "cve"], unique = true),
        Index(value = ["cve"], unique = false)
    ],
    foreignKeys = arrayOf(
        ForeignKey(
            entity = Cve::class,
            parentColumns = arrayOf("cve"),
            childColumns = arrayOf("cve"),
            onDelete = ForeignKey.CASCADE
        )
    )
)
data class Cpe(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var string: String,
    var vulnerable: Boolean,
    var cve: String
)
