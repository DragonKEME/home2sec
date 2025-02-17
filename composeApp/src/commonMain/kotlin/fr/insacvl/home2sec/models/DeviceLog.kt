package fr.insacvl.home2sec.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DeviceLog(
    // DTO
    @SerialName("log_type")
    val logType: String,
    val message: String,
    val timestamp: String,
)
