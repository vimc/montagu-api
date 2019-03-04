package org.vaccineimpact.api.app.controllers

import org.vaccineimpact.api.app.app_start.Controller
import org.vaccineimpact.api.app.context.ActionContext
import org.vaccineimpact.api.app.logic.BurdenEstimateLogic
import org.vaccineimpact.api.app.logic.RepositoriesBurdenEstimateLogic
import org.vaccineimpact.api.app.repositories.BurdenEstimateRepository
import org.vaccineimpact.api.app.repositories.Repositories
import org.vaccineimpact.api.app.requests.PostDataHelper
import org.vaccineimpact.api.app.requests.csvData
import org.vaccineimpact.api.app.security.getAllowableTouchstoneStatusList
import org.vaccineimpact.api.models.ModelRun
import org.vaccineimpact.api.models.ModelRunParameterSet
import org.vaccineimpact.api.serialization.FlexibleDataTable
import org.vaccineimpact.api.serialization.StreamSerializable
import java.time.Instant

class GroupModelRunParametersController(
        context: ActionContext,
        private val estimatesLogic: BurdenEstimateLogic,
        private val estimateRepository: BurdenEstimateRepository,
        private val postDataHelper: PostDataHelper = PostDataHelper()
) : Controller(context)
{
    constructor(context: ActionContext, repos: Repositories)
            : this(context,
            RepositoriesBurdenEstimateLogic(repos.modellingGroup, repos.burdenEstimates, repos.expectations, repos.scenario, repos.touchstone),
            repos.burdenEstimates)

    fun getModelRunParameterSets(): List<ModelRunParameterSet>
    {
        val touchstoneVersionId = context.params(":touchstone-version-id")
        val groupId = context.params(":group-id")
        estimatesLogic.validateGroupAndTouchstone(groupId, touchstoneVersionId,
                context.getAllowableTouchstoneStatusList())
        return estimateRepository.getModelRunParameterSets(groupId, touchstoneVersionId)
    }

    fun addModelRunParameters(): String
    {
        val touchstoneVersionId = context.params(":touchstone-version-id")
        val groupId = context.params(":group-id")

        estimatesLogic.validateGroupAndTouchstone(groupId, touchstoneVersionId,
                context.getAllowableTouchstoneStatusList())

        val parts = context.getParts()
        val disease = parts["disease"].contents
        val modelRuns = postDataHelper.csvData<ModelRun>(parts["file"])

        val id = estimateRepository.addModelRunParameterSet(groupId, touchstoneVersionId, disease,
                modelRuns.toList(), context.username!!, Instant.now())

        return objectCreation(context, "/modelling-groups/$groupId/model-run-parameters/$id/")
    }

    fun getModelRunParameterSet(): StreamSerializable<ModelRun>
    {
        val setId = context.params(":model-run-parameter-set-id")
        val data = getModelRunParametersDatatable()
        val filename = "set_$setId.csv"
        context.addAttachmentHeader(filename)
        return data
    }

    private fun getModelRunParametersDatatable():
            FlexibleDataTable<ModelRun>
    {
        val path = ModelRunParametersSetPath(context)
        estimatesLogic.validateGroupAndTouchstone(path.groupId, path.touchstoneVersionId,
                context.getAllowableTouchstoneStatusList())
        return estimateRepository.getModelRunParameterSet(path.groupId, path.touchstoneVersionId, path.setId)
    }

    // path parameters for model run parameters by set
    data class ModelRunParametersSetPath(val touchstoneVersionId: String, val setId: Int, val groupId: String)
    {
        constructor(context: ActionContext)
                : this(context.params(":touchstone-version-id"),
                context.params(":model-run-parameter-set-id").toInt(),
                context.params(":group-id"))

    }
}