package com.example.cvealert.data

class Cpe(
    private val cpeVersion: String = "2.3",
    private val part: Part = Part.UNDEFINED,
    private val vendor: String = "*",
    private val product: String = "*",
    private val version: String = "*",
    private val update: String = "*",
    private val edition: String = "*",
    private val language: String = "*",
    private val swEdition: String = "*",
    private val targetSoftware: String = "*",
    private val targetHardware: String = "*",
    private val other: String = "*"
) {

    companion object {

        fun generateStringFromSubscription(subscription: Subscription): String {
            val cpe = Cpe(
                vendor = subscription.vendor,
                product = subscription.product,
                version = subscription.version,
                part = subscription.part,
                update = subscription.update,
                edition = subscription.edition,
                language = subscription.language,
                swEdition = subscription.swEdition,
                targetSoftware = subscription.targetSoftware,
                targetHardware = subscription.targetHardware,
                other = subscription.other
            )

            return cpe.generateString()
        }
    }

    fun generateString(): String {
        return "cpe:${cpeVersion}:${partToString()}:${vendor}:${product}:${version}:${update}:${edition}:${language}:${swEdition}:$targetSoftware:$targetHardware:$other"

    }

    private fun partToString(): String {
        return when (part) {
            Part.APPLICATIONS -> "a"
            Part.UNDEFINED -> "*"
            Part.HARDWARE -> "h"
            else -> "o"
        }
    }


}