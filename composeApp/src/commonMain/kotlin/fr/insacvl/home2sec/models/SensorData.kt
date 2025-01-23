package fr.insacvl.home2sec.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SensorData(
    val id: Int,
    @SerialName("sensor_type")
    val sensorType: String,
    val value: Double,
    @SerialName("device_id")
    val deviceId: Int,
    val timestamp: String
)
