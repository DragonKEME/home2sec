package fr.insacvl.home2sec.ui.deviceDetails

import fr.insacvl.home2sec.models.Device
import fr.insacvl.home2sec.models.DeviceAction
import fr.insacvl.home2sec.models.DeviceLog
import fr.insacvl.home2sec.models.SensorData

data class DeviceDetailUiState(
    val device: Device? = null,
    val action: List<DeviceAction> = listOf(),
    val sensor: List<SensorData> = listOf(),
    val logs: List<DeviceLog> = listOf()
)