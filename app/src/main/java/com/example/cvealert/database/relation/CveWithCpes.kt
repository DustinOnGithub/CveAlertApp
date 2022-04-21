package com.example.cvealert.database.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.example.cvealert.database.cpe.Cpe
import com.example.cvealert.database.cve.Cve


data class CveWithCpes(
    @Embedded val cve: Cve,
    @Relation(
        parentColumn = "cve",
        entityColumn = "cve"
    )

    val cpes: List<Cpe>
)