package fr.insacvl.home2sec.data

import fr.insacvl.home2sec.models.Device
import fr.insacvl.home2sec.models.DeviceAction
import fr.insacvl.home2sec.models.DeviceLog
import fr.insacvl.home2sec.models.SensorData

interface DeviceRepository {
    var detailledDeviceId: Int?

    // Login
    suspend fun login(username: String, password: String)

    // Detected device and scan
    suspend fun get_detected_device(): List<Device>
    suspend fun scan_start()
    suspend fun delete_detected_device(device: Device)

    // Device edit
    suspend fun get_connected_device(): List<Device>
    suspend fun add_new_device(device: Device, name: String)
    suspend fun device_info(id: Int): Device

    suspend fun update_registered_device(id: Int, device: Device)
    suspend fun remove_registered_device(device: Device)

    // Sensor data
    suspend fun get_device_sensor_data(device: Device, historySize: Int = 1): List<SensorData>

    // Device Action
    suspend fun get_device_action(device: Device): List<DeviceAction>
    suspend fun do_action(device: Device, action: DeviceAction)

    // Device Logs
    suspend fun get_device_logs(device: Device): List<DeviceLog>
}