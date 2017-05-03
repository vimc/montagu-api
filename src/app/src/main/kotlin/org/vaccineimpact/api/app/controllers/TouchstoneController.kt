package org.vaccineimpact.api.app.controllers

import org.vaccineimpact.api.app.repositories.TouchstoneRepository
import org.vaccineimpact.api.models.Touchstone
import spark.Request
import spark.Response

class TouchstoneController(private val db: () -> TouchstoneRepository) : SecuredController()
{
    override val urlComponent: String = "/touchstones"
    override val endpoints = listOf(
            EndpointDefinition("/", this::getTouchstones)
    )

    fun getTouchstones(req: Request, res: Response): List<Touchstone>
    {
        return db().use { it.touchstones.all() }.toList()
    }
}