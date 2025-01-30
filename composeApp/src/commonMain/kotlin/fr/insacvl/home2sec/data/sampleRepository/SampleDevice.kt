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
        deviceMetadata = JsonObject(content = mapOf()),
        discoveredAt = "2019-05-03T22:06:56+02:00",
        ip = "10.3.141.228"
    ),
    Device(
        id = 6,
        type = "switch",
        deviceMetadata = JsonObject(content = mapOf()),
        discoveredAt = "2020-08-03T06:06:56+02:00",
        ip = "10.3.141.229"
    ),
    Device(
        id = 7,
        type = "switch",
        deviceMetadata = JsonObject(content = mapOf()),
        discoveredAt = "2021-08-03T21:06:56+02:00",
        ip = "10.3.141.230"
    ),
    Device(
        id = 10,
        type = "temperature_sensor",
        deviceMetadata = JsonObject(content = mapOf()),
        discoveredAt = "2025-05-06T22:06:56+01:00",
        ip = "10.3.141.231",
    ),
)

val sample_connected_device: List<Device> = listOf(
    Device(
        id = 2,
        name = "Living Room Sensor",
        type = "temperature_sensor",
        deviceMetadata = JsonObject(content = mapOf()),
        registeredAt = "2025-01-01T10:00:00+02:00",
        lastSeen = "2025-01-03T12:00:00+02:00",
        ip = "10.3.141.241",
        registered = true
    ),
    Device(
        id = 3,
        name = "Kitchen Room Sensor",
        type = "temperature_sensor",
        deviceMetadata = JsonObject(content = mapOf()),
        registeredAt = "2025-01-01T10:00:00+02:00",
        lastSeen = "2025-01-03T12:00:00+02:00",
        ip = "10.3.141.221",
        registered = true
    ),
    Device(
        id = 4,
        name = "Living Room switch",
        type = "switch",
        deviceMetadata = JsonObject(content = mapOf()),
        registeredAt = "2025-01-01T10:00:00+02:00",
        lastSeen = "2025-01-03T12:00:00+02:00",
        ip = "10.3.141.222",
        registered = true
    ),
    Device(
        id = 5,
        name = "Kitchen Room Switch",
        type = "switch",
        deviceMetadata = JsonObject(content = mapOf()),
        registeredAt = "2025-01-01T10:00:00+02:00",
        lastSeen = "2025-01-03T12:00:00+02:00",
        ip = "10.3.141.232",
        registered = true
    )
)

val sample_device_data: Map<Int, List<SensorData>> = mapOf(
    3 to listOf(
        SensorData(
            id = 1,
            sensorType = "temperature",
            value = 35.0,
            timestamp = "2022-05-03T22:06:56+01:00",
            deviceId = 3
        ),
        SensorData(
            id = 2,
            sensorType = "temperature",
            value = 15.0,
            timestamp = "2019-05-03T22:06:56+02:00",
            deviceId = 3
        )
    ),
    2 to listOf(
        SensorData(
            id = 3,
            sensorType = "temperature",
            value = 50.0,
            timestamp = "2019-05-03T22:06:56+02:00",
            deviceId = 2
        ),
        SensorData(
            id = 4,
            sensorType = "temperature",
            value = 1.0,
            timestamp = "2019-11-29T22:06:56+02:00",
            deviceId = 2
        )
    )
)

val sample_device_action: Map<Int,List<DeviceAction>> = mapOf(
    5 to listOf( DeviceAction(
            id = 1,
            actionName = "Turn On",
        ),
        DeviceAction(
            id = 2,
            actionName = "Turn Off",
        )
    ),
    6 to listOf( DeviceAction(
            id = 1,
            actionName = "Turn On",
        ),
        DeviceAction(
            id = 2,
            actionName = "Turn Off",
        )
    ),
    7 to listOf( DeviceAction(
            id = 1,
            actionName = "Turn On",
        ),
        DeviceAction(
            id = 2,
            actionName = "Turn Off",
        )
    ),
    8 to listOf( DeviceAction(
            id = 1,
            actionName = "Turn On",
        ),
        DeviceAction(
            id = 2,
            actionName = "Turn Off",
        )
    ),
)

val sample_device_log: Map<Int, List<DeviceLog>> = mapOf(
    5 to listOf(
        DeviceLog(
            logType = "action",
            message = "Turned On",
            timestamp = "2019-06-03T22:06:56+02:00"
        ),
        DeviceLog(
            logType = "error",
            message = "Connection lost",
            timestamp = "2019-08-03T22:41:28+02:000"
        ),
    ),
    3 to listOf(
        DeviceLog(
            logType = "info",
            message = "Temperature exceed limit",
            timestamp = "2019-09-03T22:06:56+02:00"
        ),
        DeviceLog(
            logType = "error",
            message = "Connection lost",
            timestamp = "2019-05-03T22:56:56+01:00"
        ),
    ),
)