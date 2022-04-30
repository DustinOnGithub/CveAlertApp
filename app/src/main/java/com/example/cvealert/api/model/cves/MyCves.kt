package com.example.cvealert.api.model.cves

import android.util.Log
import com.example.cvealert.database.cpe.Cpe
import com.example.cvealert.util.Constants

class MyCves : Cves() {

    fun generateDbCves(subscriptionId: Int): List<com.example.cvealert.database.cve.Cve> {

        val generatedCves: MutableList<com.example.cvealert.database.cve.Cve> = mutableListOf()
        var newCve: com.example.cvealert.database.cve.Cve

        result?.cveItems?.forEach {

            checkVersionsOfCve(it)

            //todo: null safe
            newCve = com.example.cvealert.database.cve.Cve(
                cve = getCveId(it),
                description = getDescription(it.cve),
                cvssV2String = getCvssV2String(it.impact),
                cvssV2Score = getCvssV2Score(it.impact),
                cvssV2Severity = getCvssV2Severity(it.impact),
                cvssV3String = getCvssV3String(it.impact),
                cvssV3Score = getCvssV3Score(it.impact),
                cvssV3Severity = getCvssV3Severity(it.impact),
                publishedDate = it.publishedDate,
                lastModifiedDate = it.lastModifiedDate,
                subscription_id = subscriptionId
            )

            generatedCves.add(newCve)
        }

        return generatedCves
    }

    fun generateDbCPEs(): List<Cpe> {

        val generatedCPEs: MutableList<Cpe> = mutableListOf()

        result?.cveItems?.forEach { cveItem ->

            checkVersionsOfCve(cveItem)

            if (cveItem?.configurations?.cveDataVersion != Constants.EXPECTED_CVE_DATA_VERSION) {
                Log.w(
                    TAG, "Unexpected CVE version from NVD! " +
                            "Expected ${Constants.EXPECTED_CVE_DATA_VERSION}, " +
                            "actual {$cveItem?.configurations?.cveDataVersion}"
                )
            }

            cveItem.configurations?.nodes?.forEach { cpeNode ->

                generateAndAddCpeFromCpeMatches(cpeNode?.cpeMatch, generatedCPEs, cveItem)
                generateAndAddCpeFromChildren(cpeNode.children, generatedCPEs, cveItem)
            }
        }

        return generatedCPEs
    }

    private fun generateAndAddCpeFromChildren(
        children: List<Child>,
        generatedCPEs: MutableList<Cpe>,
        cveItem: CVEItem?
    ) {

        children.forEach { child ->
            generateAndAddCpeFromCpeMatches(child.cpeMatch, generatedCPEs, cveItem)
            generateAndAddCpeFromChildren(child.children, generatedCPEs, cveItem)
        }

    }

    private fun generateAndAddCpeFromCpeMatches(
        cpeMatches: List<CpeMatch>?,
        generatedCPEs: MutableList<Cpe>,
        cveItem: CVEItem?
    ) {

        cpeMatches?.forEach { cpeMatch ->
            generatedCPEs.add(
                Cpe(
                    id = 0,
                    string = cpeMatch.cpe23Uri,
                    vulnerable = cpeMatch.vulnerable,
                    cve = getCveId(cveItem)
                )
            )

        }
    }

    private fun getCvssV3Severity(impact: Impact): String {
        return impact.baseMetricV3?.cvssV3?.baseSeverity ?: ""
    }

    private fun getCvssV2Severity(impact: Impact): String {
        return impact.baseMetricV2?.severity ?: ""
    }

    private fun getCvssV3Score(impact: Impact): Float {
        return impact.baseMetricV3?.cvssV3?.baseScore?.toFloat() ?: Constants.NO_CVSS_SCORE
    }

    private fun getCvssV2Score(impact: Impact): Float {
        return impact.baseMetricV2?.cvssV2?.baseScore?.toFloat() ?: Constants.NO_CVSS_SCORE
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

    private fun checkVersionsOfCve(cveItem: CVEItem?) {
        if (cveItem?.configurations?.cveDataVersion != Constants.EXPECTED_CVE_DATA_VERSION) {
            Log.w(
                TAG, "Unexpected CVE version in ${cveItem?.cve?.cveDataMeta?.id} from NVD! " +
                        "Expected ${Constants.EXPECTED_CVE_DATA_VERSION}, " +
                        "actual ${cveItem?.configurations?.cveDataVersion}"
            )
        }
    }

    private fun getCveId(cveItem: CVEItem?): String {
        return cveItem?.cve?.cveDataMeta?.id ?: ""
    }

    companion object {
        private const val TAG = "MyCves"
    }

}