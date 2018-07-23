package org.vaccineimpact.api.app.repositories

import org.vaccineimpact.api.app.filters.ScenarioFilterParameters
import org.vaccineimpact.api.db.tables.records.ResponsibilitySetRecord
import org.vaccineimpact.api.models.responsibilities.ResponsibilityAndTouchstone
import org.vaccineimpact.api.models.responsibilities.ResponsibilitySet

interface ResponsibilitiesRepository: Repository {
    fun getResponsibilitiesForTouchstone(touchstoneVersionId: String): List<ResponsibilitySet>
    fun getResponsibilities(responsibilitySet: ResponsibilitySetRecord?,
                            scenarioFilterParameters: ScenarioFilterParameters,
                            touchstoneVersionId: String,
                            modellingGroupId: String): ResponsibilitySet
    fun getResponsibility(groupId: String, touchstoneVersionId: String, scenarioId: String): ResponsibilityAndTouchstone
    fun getResponsibilitiesForGroup(groupId: String,
                                    touchstoneVersionId: String,
                                    scenarioFilterParameters: ScenarioFilterParameters): ResponsibilitySet

    fun getResponsibilityId(groupId: String, touchstoneVersionId: String, scenarioId: String): Int
}