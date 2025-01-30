package fr.insacvl.home2sec.data.APIRepository

import fr.insacvl.home2sec.data.APIRepository.DTO.Token
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
import io.ktor.client.request.put
import io.ktor.http.HttpHeaders
import io.ktor.http.headers
import io.ktor.http.isSuccess

class ApiRepository (
    val httpClient: HttpClient
): DeviceRepository{
    val BASE_URL = "http://10.3.141.1:8000"

    var token: String? = null

    override var detailledDeviceId: Int? = null

    override suspend fun login(username: String, password: String) {
        val response = httpClient.post("$BASE_URL/api/login?username=${username}&password=${password}")
        if (!response.status.isSuccess()) {
            throw InternalError(InternalErrorKind.LOGIN_FAIL)
        }
        val tokenDto: Token = response.body()
        this.token = "Bearer " + tokenDto.accessToken
    }

    override suspend fun get_detected_device(): List<Device> {

        val devices: List<Device> = httpClient.get("$BASE_URL/api/detected-devices"){
            headers {
                append(HttpHeaders.Authorization, token!!)
            }
        }.body()
        return devices
    }

    override suspend fun scan_start() {
        val response = httpClient.post("$BASE_URL/api/detected-devices/scan"){
            headers {
                append(HttpHeaders.Authorization, token!!)
            }
        }
        if (!response.status.isSuccess()) {
            throw InternalError(InternalErrorKind.SCAN_FAIL)
        }
    }

    override suspend fun delete_detected_device(device: Device) {
        TODO("Not yet implemented")
    }

    override suspend fun get_connected_device(): List<Device> {
        val devices: List<Device> = httpClient.get("$BASE_URL/api/devices"){
            headers {
                append(HttpHeaders.Authorization, token!!)
            }
        }.body()
        return devices.map { it.registered = true; it}
    }

    override suspend fun add_new_device(device: Device, name: String) {
        val response = httpClient.post("$BASE_URL/api/devices?detected_device_id=${device.id}&name=$name"){
            headers {
                append(HttpHeaders.Authorization, token!!)
            }
        }
        if (!response.status.isSuccess()){
            throw InternalError(InternalErrorKind.ADD_FAIL)
        }
    }

    override suspend fun device_info(id: Int): Device {
        val device: Device = httpClient.get("$BASE_URL/api/device/$id"){
            headers {
                append(HttpHeaders.Authorization, token!!)
            }
        }.body()
        return device
    }

    override suspend fun update_registered_device(id: Int, device: Device) {
        val response = httpClient.put("$BASE_URL/api/device/${id}?name=${device.name}"){
            headers {
                append(HttpHeaders.Authorization, token!!)
            }
        }
        if (!response.status.isSuccess()) {
            throw InternalError(InternalErrorKind.SERVER_ERROR)
        }
    }

    override suspend fun remove_registered_device(device: Device) {
        val response = httpClient.delete("$BASE_URL/api/device/${device.id}"){
            headers {
                append(HttpHeaders.Authorization, token!!)
            }
        }
        if (!response.status.isSuccess()){
            throw InternalError(InternalErrorKind.SERVER_ERROR)
        }
    }

    override suspend fun get_device_sensor_data(device: Device, historySize: Int): List<SensorData> {
        val sensorsDatas: List<SensorData> = httpClient
            .get("$BASE_URL/api/device/${device.id}/sensor_data/1"){
                headers {
                    append(HttpHeaders.Authorization, token!!)
                }
            }.body() // Fix to 1 cause sensor history
        return sensorsDatas
    }

    override suspend fun get_device_action(device: Device): List<DeviceAction> {
        val actions: List<DeviceAction> = httpClient
            .get("$BASE_URL/api/device/${device.id}/actions"){
                headers {
                    append(HttpHeaders.Authorization, token!!)
                }
            }.body()
        return actions
    }

    override suspend fun do_action(device: Device, action: DeviceAction) {
        val response = httpClient.post("$BASE_URL/api/device/${device.id}/action/${action.id}"){
            headers {
                append(HttpHeaders.Authorization, token!!)
            }
        }
        if (!response.status.isSuccess()){
            throw InternalError(InternalErrorKind.SERVER_ERROR)
        }
    }

    override suspend fun get_device_logs(device: Device): List<DeviceLog> {
        val logs: List<DeviceLog> = httpClient
            .get("$BASE_URL/api/device/${device.id}/logs/3"){
                headers {
                    append(HttpHeaders.Authorization, token!!)
                }
            }.body() // TODO: change "3" to a parameter value
        return logs
    }

}