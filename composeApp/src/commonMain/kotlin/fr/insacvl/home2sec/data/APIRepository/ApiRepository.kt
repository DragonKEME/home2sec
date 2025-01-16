package fr.insacvl.home2sec.data.APIRepository

import fr.insacvl.home2sec.data.DeviceRepository
import fr.insacvl.home2sec.models.Device
import fr.insacvl.home2sec.models.DeviceAction
import fr.insacvl.home2sec.models.DeviceLog
import fr.insacvl.home2sec.models.SensorData
import fr.insacvl.home2sec.models.errors.InternalError
import fr.insacvl.home2sec.models.errors.InternalErrorKind
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.http.isSuccess

class ApiRepository (
    val httpClient: HttpClient
): DeviceRepository{
    val BASE_URL = "http://10.3.141.1:8000"

    override suspend fun get_detected_device(): List<Device> {
        val devices: List<Device> = httpClient.get("$BASE_URL/api/detected-devices").body()
        return devices
    }

    override suspend fun scan_start() {
        if (!httpClient.post("$BASE_URL/api/detected-devices/scan").status.isSuccess()) {
            throw InternalError(InternalErrorKind.SCAN_FAIL)
        }
    }

    override suspend fun delete_detected_device(device: Device) {
        TODO("Not yet implemented")
    }

    override suspend fun get_connected_device(): List<Device> {
        val devices: List<Device> = httpClient.get("$BASE_URL/api/devices").body()
        return devices.map { it.registered = true; it}
    }

    override suspend fun add_new_device(device: Device, name: String) {
        if (!httpClient.post("$BASE_URL/api/devices?detected_device_id=${device.id}&name=$name").status.isSuccess()){
            throw InternalError(InternalErrorKind.ADD_FAIL)
        }
    }

    override suspend fun device_info(id: Int): Device {
        val device: Device = httpClient.get("$BASE_URL/api/device/$id").body()
        return device
    }

    override suspend fun update_registered_device(id: Int, device: Device) {
        TODO("Not yet implemented")
    }

    override suspend fun remove_registered_device(device: Device) {
        if (!httpClient.delete("$BASE_URL/api/device/${device.id}").status.isSuccess()){
            throw InternalError(InternalErrorKind.SERVER_ERROR)
        }
    }

    override suspend fun get_device_sensor_data(device: Device): List<SensorData> {
        TODO("Not yet implemented")
    }

    override suspend fun get_device_action(device: Device): List<DeviceAction> {
        TODO("Not yet implemented")
    }

    override suspend fun do_action(device: Device, action: DeviceAction) {
        TODO("Not yet implemented")
    }

    override suspend fun get_device_logs(device: Device): List<DeviceLog> {
        TODO("Not yet implemented")
    }

}