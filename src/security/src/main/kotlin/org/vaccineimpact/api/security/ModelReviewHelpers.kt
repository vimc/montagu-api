package org.vaccineimpact.api.security

import org.vaccineimpact.api.db.getResource

fun getGroupReviewersMap(): Map<String, List<String>> = getResource("model-review-2019")
        .readText()
        .split("\n")
        .map {
            val row = it.split(",")
            row[0] to row.subList(1, row.count())
        }.toMap()

fun getDiseaseReviewersMap(): Map<String, List<String>> = getResource("model-review-diseases-2019")
        .readText()
        .split("\n")
        .map {
            val row = it.split(",")
            row[0] to row.subList(1, row.count())
        }.toMap()
