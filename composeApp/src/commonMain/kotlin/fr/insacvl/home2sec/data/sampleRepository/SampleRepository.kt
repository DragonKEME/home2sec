package fr.insacvl.home2sec.data.sampleRepository

import fr.insacvl.home2sec.data.DeviceRepository
import fr.insacvl.home2sec.models.Device
import fr.insacvl.home2sec.models.DeviceAction
import fr.insacvl.home2sec.models.DeviceLog
import fr.insacvl.home2sec.models.SensorData
import fr.insacvl.home2sec.models.errors.InternalError
import fr.insacvl.home2sec.models.errors.InternalErrorKind

/**
 * Sample data repository
 *
 * Repository contenant des données d'exemple pour tester l'application sans serveur.
 */
class SampleRepository: DeviceRepository {
    private var scanStarted: Boolean = false
    private var localDetectedDevice: MutableList<Device> = mutableListOf()

    private var connected: Boolean = false
    private var localConnectedDevice: MutableList<Device> = mutableListOf()

    override var detailledDeviceId: Int? = null

    // login
    override suspend fun login(username: String, password: String) {
        if (username == "test" && password == "feur"){
            return
        }
        throw InternalError(InternalErrorKind.LOGIN_FAIL)
    }

    // Detected device management
    override suspend fun get_detected_device(): List<Device> {
        return ArrayList(localDetectedDevice.map { it.copy() })
    }

    override suspend fun scan_start() {
        scanStarted = true
        localDetectedDevice = sample_detected_device.toMutableList()
    }

    override suspend fun delete_detected_device(device: Device) {
        val deletedDevice = localDetectedDevice.find { it.id == device.id} ?: throw InternalError(InternalErrorKind.UNKNOWN_DEVICE)
        localDetectedDevice.remove(deletedDevice)
    }

    // Connected device
    override suspend fun get_connected_device(): List<Device> {
        if (!connected) {
            connected = true
            localConnectedDevice = sample_connected_device.toMutableList()
        }
        return ArrayList(localConnectedDevice.map { it.copy() })
    }

    override suspend fun add_new_device(device: Device, name: String) {
        device.name = name
        device.registeredAt = "2025-01-13T11:32:00+01:00"
        device.registered = true
        localConnectedDevice.add(device)
        val deviceRemoved = localDetectedDevice.find { it.id == device.id }
        localDetectedDevice.remove(deviceRemoved)
    }

    override suspend fun device_info(id: Int): Device {
        return localConnectedDevice.find { it.id == id}?.copy() ?: throw InternalError(InternalErrorKind.UNKNOWN_DEVICE)
    }

    override suspend fun update_registered_device(id: Int, device: Device) {
        val deletedDevice = localConnectedDevice.find { it.id == id} ?: throw InternalError(InternalErrorKind.UNKNOWN_DEVICE)
        val newDevice = deletedDevice.copy(name = device.name)
        localConnectedDevice.remove(deletedDevice)
        localConnectedDevice.add(newDevice)
    }

    override suspend fun remove_registered_device(device: Device) {
        val deletedDevice = localConnectedDevice.find { it.id == device.id} ?: throw InternalError(InternalErrorKind.UNKNOWN_DEVICE)
        localConnectedDevice.remove(deletedDevice)
    }

    override suspend fun get_device_sensor_data(device: Device, historySize: Int): List<SensorData> {
        return sample_device_data[device.id]?.toList() ?: listOf()
    }

    override suspend fun get_device_action(device: Device): List<DeviceAction> {
        return sample_device_action[device.id]?.toList() ?: listOf()
    }

    override suspend fun do_action(device: Device, action: DeviceAction) {
        println("Action \"" + action.actionName + "\" done")
    }

    override suspend fun get_device_logs(device: Device): List<DeviceLog> {
        return sample_device_log[device.id]?.toList() ?: listOf()
    }
}