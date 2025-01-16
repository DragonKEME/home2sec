package fr.insacvl.home2sec.ui.listScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.insacvl.home2sec.data.DeviceRepository
import fr.insacvl.home2sec.models.Device
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.job
import kotlinx.coroutines.launch

enum class VoleyState {
    UP,
    DOWN,
    NOP
}

class ListViewModel(
    private val deviceRepository: DeviceRepository
): ViewModel() {

    var uiState: ListUiState by mutableStateOf (ListUiState.LoadingState)
        private set

    private var refreshScanJob: Job? = null

    private var previousUiState: ListUiState? = null

    init {
        load_connected_device()
    }

    fun update_volet(voleyState: VoleyState){

        if (uiState is ListUiState.ListState) {
            update_action_ui_state(voleyState, null, null,null,)
        }
    }

    fun load_connected_device() {
        println("test")
        viewModelScope.launch {
            update_connected_device()
        }
    }

    fun start_scan() {
        println("Scan start")
        viewModelScope.launch {
            deviceRepository.scan_start()
            println("Scan started")
        }
        update_action_ui_state(null, null, null, true)
        // TODO: Better timer function
        this.refreshScanJob = CoroutineScope(Dispatchers.Main).launch {
            while (true){
                delay(1000)
                update_scan()
            }
        }
    }

    fun stop_scan() {
        kill_update()
        update_action_ui_state(null, null, null, false)
    }

    private fun kill_update() {
        refreshScanJob?.job?.cancel()
    }

    private fun update_scan() {
        viewModelScope.launch {
            println("Scan device")
            val scannedDevices = deviceRepository.get_detected_device()
            update_action_ui_state(null, null, scannedDevices, null)
            println("Scanned device: " + scannedDevices.size)
        }
    }

    fun get_device_info(device: Device) {
        viewModelScope.launch {
            previousUiState = uiState
            val deviceInfo = deviceRepository.device_info(device.id)
            val currentState = uiState
            if (currentState is ListUiState.ListState){
                uiState = ListUiState.DeviceInfo(listState = currentState, device = deviceInfo)
            }else{
                uiState = ListUiState.DeviceInfo(device = device)
            }

        }
        kill_update()
    }

    fun register_device(device: Device){
        viewModelScope.launch {
            deviceRepository.add_new_device(device, name = "feurfeur") // TODO: Change that
            update_connected_device()
        }

    }

    fun dismiss_device_info(){
        val previousUiState = this.previousUiState
        if (previousUiState != null && previousUiState is ListUiState.ListState){
            uiState = previousUiState
        }else{
            load_connected_device()
        }
    }

    fun delete_connected_device(device: Device){
        println("delete device: " + device.id)
        viewModelScope.launch {
            deviceRepository.remove_registered_device(device)
            update_connected_device()
        }
    }

    fun delete_detected_device(device: Device){
        println("delete device: " + device.id)
        viewModelScope.launch {
            deviceRepository.delete_detected_device(device)
        }
    }

    private suspend fun update_connected_device() {
        val connectedDevices = deviceRepository.get_connected_device()
        println("Device connected: " + connectedDevices.size)
        update_action_ui_state(null, connectedDevices, null, null)
    }

    private fun update_action_ui_state(voletState: VoleyState?,
                                       connectedDevices: List<Device>?,
                                       scannedDevices: List<Device>?,
                                       scanStarted: Boolean?
    ){
        if (uiState is ListUiState.ListState) {
            // If uistate il already action state
            val oldUiState = uiState as ListUiState.ListState
            uiState = ListUiState.ListState(
                voletState ?: oldUiState.voletState,
                connectedDevices ?: oldUiState.connectedDevices,
                scannedDevices ?: oldUiState.scannedDevices,
                scanStarted ?: oldUiState.scanStarted
            )
        } else {
            // Default action state
            uiState = ListUiState.ListState(
                voletState ?: VoleyState.NOP,
                connectedDevices ?: listOf(),
                scannedDevices ?: listOf(),
                scanStarted ?: false
            )
        }
    }

}