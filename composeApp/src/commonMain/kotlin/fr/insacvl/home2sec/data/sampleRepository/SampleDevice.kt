package fr.insacvl.home2sec.data.sampleRepository

import fr.insacvl.home2sec.models.Device
import fr.insacvl.home2sec.models.DeviceAction
import fr.insacvl.home2sec.models.DeviceLog
import fr.insacvl.home2sec.models.SensorData
import kotlinx.serialization.json.JsonObject


val sample_detected_device : List<Device> = listOf(
    Device(
        id = 1,
        type = "temperature_sensor",
        metadata = JsonObject(content = mapOf()),
        discoveredAt = "2025-01-13T12:34:56"
    ),
    Device(
        id = 6,
        type = "switch",
        metadata = JsonObject(content = mapOf()),
        discoveredAt = "2025-01-13T12:34:56"
    ),
    Device(
        id = 7,
        type = "switch",
        metadata = JsonObject(content = mapOf()),
        discoveredAt = "2025-01-13T12:34:56"
    ),
    Device(
        id = 10,
        type = "temperature_sensor",
        metadata = JsonObject(content = mapOf()),
        discoveredAt = "2025-01-13T12:34:56"
    ),
)

val sample_connected_device: List<Device> = listOf(
    Device(
        id = 2,
        name = "Living Room Sensor",
        type = "temperature_sensor",
        metadata = JsonObject(content = mapOf()),
        registeredAt = "2025-01-01T10:00:00",
        lastSeen = "2025-01-03T12:00:00",
        registered = true
    ),
    Device(
        id = 3,
        name = "Kitchen Room Sensor",
        type = "temperature_sensor",
        metadata = JsonObject(content = mapOf()),
        registeredAt = "2025-01-01T10:00:00",
        lastSeen = "2025-01-03T12:00:00",
        registered = true
    ),
    Device(
        id = 4,
        name = "Living Room switch",
        type = "switch",
        metadata = JsonObject(content = mapOf()),
        registeredAt = "2025-01-01T10:00:00",
        lastSeen = "2025-01-03T12:00:00",
        registered = true
    ),
    Device(
        id = 5,
        name = "Kitchen Room Switch",
        type = "switch",
        metadata = JsonObject(content = mapOf()),
        registeredAt = "2025-01-01T10:00:00",
        lastSeen = "2025-01-03T12:00:00",
        registered = true
    )
)

val sample_device_data: Map<Int, List<SensorData>> = mapOf(
    3 to listOf(
        SensorData(
            dataType = "temperature",
            value = 35.0,
            timestamp = "2025-01-03T12:00:00"
        ),
        SensorData(
            dataType = "temperature",
            value = 15.0,
            timestamp = "2025-01-03T12:00:00"
        )
    ),
    2 to listOf(
        SensorData(
            dataType = "temperature",
            value = 50.0,
            timestamp = "2025-01-03T12:00:00"
        ),
        SensorData(
            dataType = "temperature",
            value = 1.0,
            timestamp = "2025-01-03T12:00:00"
        )
    )
)

val sample_device_action: Map<Int,List<DeviceAction>> = mapOf(
    5 to listOf( DeviceAction(
            id = 1,
            actionName = "Turn On",
            command = "ON"
        ),
        DeviceAction(
            id = 2,
            actionName = "Turn Off",
            command = "OFF"
        )
    ),
    6 to listOf( DeviceAction(
            id = 1,
            actionName = "Turn On",
            command = "ON"
        ),
        DeviceAction(
            id = 2,
            actionName = "Turn Off",
            command = "OFF"
        )
    ),
    7 to listOf( DeviceAction(
            id = 1,
            actionName = "Turn On",
            command = "ON"
        ),
        DeviceAction(
            id = 2,
            actionName = "Turn Off",
            command = "OFF"
        )
    ),
    8 to listOf( DeviceAction(
            id = 1,
            actionName = "Turn On",
            command = "ON"
        ),
        DeviceAction(
            id = 2,
            actionName = "Turn Off",
            command = "OFF"
        )
    ),
)

val sample_device_log: Map<Int, List<DeviceLog>> = mapOf(
    5 to listOf(
        DeviceLog(
            logType = "action",
            message = "Turned On",
            timestamp = "2025-01-03T12:10:00"
        ),
        DeviceLog(
            logType = "error",
            message = "Connection lost",
            timestamp = "2025-01-03T12:15:00"
        ),
    ),
    3 to listOf(
        DeviceLog(
            logType = "info",
            message = "Temperature exceed limit",
            timestamp = "2025-01-03T13:10:00"
        ),
        DeviceLog(
            logType = "error",
            message = "Connection lost",
            timestamp = "2025-01-03T123:15:00"
        ),
    ),
)