package com.example.cvealert.api.model.cves

class MyCves : Cves() {

    fun generateDbCves(): List<com.example.cvealert.data.cve.Cve> {

        val generatedCves: MutableList<com.example.cvealert.data.cve.Cve> = mutableListOf()
        var newCve: com.example.cvealert.data.cve.Cve

        result.cveItems.forEach {

            newCve = com.example.cvealert.data.cve.Cve(
                cve = it.cve.cveDataMeta.id,
                description = getDescription(it.cve),
                cvssV2String = getCvssV2String(it.impact),
                cvssV2Score = getCvssV2Score(it.impact).toFloat(),
                cvssV2Severity = getCvssV2Severity(it.impact),
                cvssV3String = getCvssV3String(it.impact),
                cvssV3Score = getCvssV3Score(it.impact).toFloat(),
                cvssV3Severity = getCvssV3Severity(it.impact),
                publishedDate = it.publishedDate,
                lastModifiedDate = it.lastModifiedDate
            )

            generatedCves.add(newCve)
        }


        return generatedCves
    }

    private fun getCvssV3Severity(impact: Impact): String {
        return impact.baseMetricV3?.cvssV3?.baseSeverity ?: ""
    }

    private fun getCvssV2Severity(impact: Impact): String {
        return impact.baseMetricV2?.severity ?: ""
    }

    private fun getCvssV3Score(impact: Impact): Double {
        return impact.baseMetricV3?.cvssV3?.baseScore ?: 100.0
    }

    private fun getCvssV2Score(impact: Impact): Double {
        return impact.baseMetricV2?.cvssV2?.baseScore ?: 100.0
    }

    private fun getCvssV3String(impact: Impact): String {
        return impact.baseMetricV3?.cvssV3?.vectorString ?: ""
    }

    private fun getCvssV2String(impact: Impact): String {
        return impact.baseMetricV2?.cvssV2?.vectorString ?: ""
    }

    private fun getDescription(cve: Cve): String {

        cve.description?.descriptionData?.forEach { descriptionData ->
            if (descriptionData?.lang == "en") {
                return descriptionData.value
            }
        }

        return ""
    }

}