package fr.insacvl.home2sec.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DeviceAction(
    val id: Int,
    @SerialName("command_name")
    val actionName: String,

    var actionRunning: Boolean = false
)
