package com.example.cvealert.database.subscription

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize


enum class Part {
    UNDEFINED,
    APPLICATIONS,
    HARDWARE,
    OPERATING_SYSTEM
}

@Parcelize
@Entity(
    tableName = "subscription",
    indices = [
        Index(
            value = [
                "vendor", "product", "part", "version", "update", "edition",
                "sw_edition", "target_software", "target_hardware", "language"
            ],
            unique = true
        )
    ]
)
class Subscription(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var vendor: String = "",
    var product: String = "",
    @ColumnInfo(name = "push_up_notification")
    var pushUpNotification: Boolean = true,
    var part: Part = Part.UNDEFINED,
    var version: String = "",
    var update: String = "",
    @ColumnInfo(name = "is_active")
    var isActive: Boolean = true,
    var edition: String = "",
    val language: String = "",
    @ColumnInfo(name = "sw_edition")
    var swEdition: String = "",
    @ColumnInfo(name = "target_software")
    val targetSoftware: String = "",
    @ColumnInfo(name = "target_hardware")
    val targetHardware: String = "",
    val other: String = ""
) : Parcelable {

    /**
     * checks if the subscription is valid to safe in db.
     * checks if all required fields are filled
     */
    fun isValid(): Boolean {

        if (part == Part.UNDEFINED) {
            return false
        }
        if (vendor.isEmpty() || vendor == "*") {
            return false
        }
        if (product.isEmpty() || product == "*") {
            return false
        }

        return true
    }
}