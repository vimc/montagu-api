package org.vaccineimpact.api.test_helpers

import org.vaccineimpact.api.models.*
import org.vaccineimpact.api.models.expectations.CohortRestriction
import org.vaccineimpact.api.models.expectations.CountryOutcomeExpectations
import org.vaccineimpact.api.models.expectations.ExpectationMapping
import org.vaccineimpact.api.models.expectations.OutcomeExpectations
import org.vaccineimpact.api.models.responsibilities.*
import org.vaccineimpact.api.security.InternalUser
import org.vaccineimpact.api.security.UserProperties

fun exampleInternalUser(username: String = "username") = InternalUser(
        UserProperties(
                username,
                name = "Full name",
                email = "email@example.com",
                lastLoggedIn = null,
                passwordHash = null
        ),
        emptyList(),
        emptyList()
)

fun exampleExpectations(id: Int=1) = CountryOutcomeExpectations(
        id = id,
        description = "description",
        years = 2000..2100,
        ages = 0..99,
        cohorts = CohortRestriction(null, null),
        countries = emptyList(),
        outcomes = emptyList()
)


fun exampleOutcomeExpectations(id: Int=1, outcomes:List<Outcome> = emptyList()) = OutcomeExpectations(
        id = id,
        description = "description",
        years = 2000..2100,
        ages = 0..99,
        cohorts = CohortRestriction(null, null),
        outcomes = outcomes
)

fun exampleExpectationMapping() = ExpectationMapping(
        exampleExpectations(),
        listOf("yf-scenario", "yf-scenario-2"),
        "YF"
)

fun exampleResponsibility() = Responsibility(
        exampleScenario(),
        ResponsibilityStatus.EMPTY,
        emptyList(),
        null
)

fun exampleTouchstoneVersion(
        id: String = "touchstone-1",
        status: TouchstoneStatus = TouchstoneStatus.OPEN
) = TouchstoneVersion(
        id = id,
        name = "touchstone",
        version = 1,
        description = "Some example touchstone version",
        status = status
)

fun exampleScenario() = Scenario(
        id = "yf-scenario",
        description = "Some example scenario",
        disease = "YF",
        touchstones = listOf("touchstone-1")
)

fun exampleResponsibilitySet(touchstoneVersionId: String, groupId: String) = ResponsibilitySet(
        touchstoneVersionId,
        groupId,
        ResponsibilitySetStatus.INCOMPLETE,
        listOf(exampleResponsibility(), exampleResponsibility())
)

fun exampleResponsibilitySetWithExpectations(touchstoneId: String, groupId: String) = ResponsibilitySetWithExpectations(
        touchstoneId,
        groupId,
        ResponsibilitySetStatus.INCOMPLETE,
        emptyList(),
        listOf(exampleExpectationMapping())
)