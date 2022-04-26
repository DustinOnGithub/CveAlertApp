package com.example.cvealert.util

import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class Constants {

    companion object {
        const val BASE_NVD_URL = "https://services.nvd.nist.gov"
        const val EXPECTED_CVE_DATA_VERSION = "4.0"
        const val WORKER_REPEAT_INTERVAL: Long = 60 //minutes


        fun getLastCVEDateTime(): String {
            return ZonedDateTime.now(TimeZone.getDefault().toZoneId()).minusMonths(1)
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss:SSS 'UTC'xxx"))
        }
    }

}