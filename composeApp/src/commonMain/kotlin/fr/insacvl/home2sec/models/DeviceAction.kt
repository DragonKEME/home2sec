package fr.insacvl.home2sec.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DeviceAction(
    val id: Int,
    @SerialName("action_name")
    val actionName: String,
    val command: String,
)
