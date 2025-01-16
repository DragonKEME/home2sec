package fr.insacvl.home2sec.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SensorData(
    @SerialName("data_type")
    val dataType: String,
    val value: Double,
    val timestamp: String
)
