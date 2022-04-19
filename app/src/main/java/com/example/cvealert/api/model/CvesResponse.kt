package com.example.cvealert.api.model

data class CvesResponse(
    val resultsPerPage: UInt,
    val startIndex: UInt,
    val totalResults: UInt,
)
