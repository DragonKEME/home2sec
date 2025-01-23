package fr.insacvl.home2sec.ui.deviceDetails

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.insacvl.home2sec.data.DeviceRepository
import fr.insacvl.home2sec.models.Device
import fr.insacvl.home2sec.models.DeviceAction
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class DeviceDetailViewModel(
    private val deviceRepository: DeviceRepository,

): ViewModel() {
    var uiState: DeviceDetailUiState by mutableStateOf(DeviceDetailUiState())
        private set

    init {
        load_device_detail()
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
                uiState = DeviceDetailUiState(
                    device = device,
                    action = deviceActions.await(),
                    sensor = deviceSensors.await(),
                    logs = deviceLogs.await()
                )
            }
        }
    }

    fun do_action(device: Device, action: DeviceAction){
        viewModelScope.launch {
            deviceRepository.do_action(device, action)
        }
    }
}