package com.example.cvealert.data.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.example.cvealert.data.cpe.Cpe
import com.example.cvealert.data.cve.Cve


data class CveWithCpes(
    @Embedded val cve: Cve,
    @Relation(
        parentColumn = "cve",
        entityColumn = "cve"
    )

    val cpes: List<Cpe>
)