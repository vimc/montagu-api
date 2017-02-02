package uk.ac.imperial.vimc.demo.app.viewmodels

import uk.ac.imperial.vimc.demo.app.models.ModellingGroup

@Suppress("Unused")
class ModellingGroupMetadata(group: ModellingGroup) {
    val id = group.id
    val description = group.description
}