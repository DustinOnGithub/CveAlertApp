package com.example.cvealert.database.cpe

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.cvealert.database.cve.Cve
import kotlinx.parcelize.Parcelize


@Parcelize
@Entity(
    tableName = "cpe",
    indices = [
        Index(value = ["string", "vulnerable", "cve"], unique = true),
        Index(value = ["cve"], unique = false)
    ],
    foreignKeys = [ForeignKey(
        entity = Cve::class,
        parentColumns = ["cve"],
        childColumns = ["cve"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class Cpe(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var string: String,
    var vulnerable: Boolean,
    var cve: String
) : Parcelable
