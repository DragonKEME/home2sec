package fr.insacvl.home2sec.ui.deviceDetails

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.insacvl.home2sec.data.DeviceRepository
import fr.insacvl.home2sec.models.Device
import fr.insacvl.home2sec.models.DeviceAction
import fr.insacvl.home2sec.models.DeviceLog
import fr.insacvl.home2sec.models.SensorData
import fr.insacvl.home2sec.utils.DateUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class DeviceDetailViewModel(
    private val deviceRepository: DeviceRepository,
    private val dateUtils: DateUtils
): ViewModel() {
    var uiState: DeviceDetailUiState by mutableStateOf(DeviceDetailUiState.DeviceInfo())
        private set

    private var refreshSensorJob: Job? = null

    var editDeviceName by mutableStateOf("")
        private set


    init {
        load_device_detail()
        lauch_sensor_refresh_job()
    }

    override fun onCleared() {
        super.onCleared()
        kill_sensor_refresh_job()
    }

    fun load_device_detail(){
        viewModelScope.launch {
            val deviceId = deviceRepository.detailledDeviceId ?: return@launch
            coroutineScope {
                val device = deviceRepository.device_info(deviceId)
                val deviceActions = async {
                    deviceRepository.get_device_action(device)
                }
                val deviceSensors = async {
                    deviceRepository.get_device_sensor_data(device)
                }
                val deviceLogs = async {
                    deviceRepository.get_device_logs(device)
                }
                val ra = device.registeredAt
                if (ra != null) {
                    device.registeredAt = dateUtils.FormatDate(ra)
                }
                val ls = device.lastSeen
                if (ls != null) {
                    device.lastSeen = dateUtils.FormatDate(ls)
                }
                uiState = DeviceDetailUiState.DeviceInfo(
                    device = device,
                    action = deviceActions.await(),
                    sensor = deviceSensors.await(),
                    logs = deviceLogs.await()
                )
            }
        }
    }

    fun lauch_sensor_refresh_job() {
        refreshSensorJob = CoroutineScope(Dispatchers.Main).launch {
            while (true) {
                val currentUiState = uiState
                if (currentUiState is DeviceDetailUiState.DeviceInfo && currentUiState.device != null) {
                    val sensorDatas =
                        deviceRepository.get_device_sensor_data(device = currentUiState.device)
                    update_device_info_ui_state(sensor = sensorDatas)
                }
                delay(1000)
            }
        }
    }

    fun kill_sensor_refresh_job() {
        this.refreshSensorJob?.cancel()
        this.refreshSensorJob = null
    }

    fun do_action(device: Device, action: DeviceAction){
        viewModelScope.launch {
            deviceRepository.do_action(device, action)
        }
    }

    fun change_name_screen(){
        val oldUiState = this.uiState
        if (oldUiState is DeviceDetailUiState.DeviceInfo && oldUiState.device != null){
            uiState = DeviceDetailUiState.EditDeviceName(oldUiState)
            editDeviceName = oldUiState.device.name ?: ""
        }
    }

    fun update_name_string(name: String){
        editDeviceName = name
    }

    fun dismiss_update(){
        val currentUiState = uiState
        if (currentUiState is DeviceDetailUiState.EditDeviceName){
            uiState = currentUiState.deviceInfo
        }
        editDeviceName = ""
    }

    fun update_device(){
        val currentUiState = uiState
        if (currentUiState !is DeviceDetailUiState.EditDeviceName){
            return
        }
        val device = currentUiState.deviceInfo.device!! // Must exist
        device.name = editDeviceName
        viewModelScope.launch {
            deviceRepository.update_registered_device(device.id, device)
        }
    }

    private fun update_device_info_ui_state(
        device: Device? = null,
        action: List<DeviceAction>? = null,
        sensor: List<SensorData>? = null,
        logs: List<DeviceLog>? = null
    ){
        val currentUiState = uiState
        uiState = if (currentUiState is DeviceDetailUiState.DeviceInfo){
            DeviceDetailUiState.DeviceInfo(
                device ?: currentUiState.device,
                action ?: currentUiState.action,
                sensor ?: currentUiState.sensor,
                logs ?: currentUiState.logs
            )
        } else {
            DeviceDetailUiState.DeviceInfo(
                device,
                action ?: listOf(),
                sensor ?: listOf(),
                logs ?: listOf()
            )
        }
    }
}