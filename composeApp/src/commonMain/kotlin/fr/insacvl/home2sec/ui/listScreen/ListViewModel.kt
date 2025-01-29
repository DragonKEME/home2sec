package fr.insacvl.home2sec.ui.listScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.insacvl.home2sec.data.DeviceRepository
import fr.insacvl.home2sec.models.Device
import fr.insacvl.home2sec.utils.DateUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.job
import kotlinx.coroutines.launch


class ListViewModel(
    private val deviceRepository: DeviceRepository,
    private val dateUtils: DateUtils
): ViewModel() {

    var uiState: ListUiState by mutableStateOf (ListUiState.LoadingState)
        private set

    private var refreshScanJob: Job? = null

    var registerDeviceName by mutableStateOf("")
        private set

    init {
        load_connected_device()
    }

    fun load_connected_device() {
        viewModelScope.launch {
            update_connected_device()
        }
    }

    fun start_scan() {
        println("Scan start")
        // Launch async job
        this.refreshScanJob = CoroutineScope(Dispatchers.Main).launch  {
            // The device repository will lock until the server finish the scan.
            deviceRepository.scan_start()
            println("Scan Finished")
            // Update scanned device.
            update_scan()
            update_action_ui_state(scanStarted = false)
        }
        // Set device loading
        update_action_ui_state(scanStarted = true)
    }

    fun stop_scan() {
        refreshScanJob?.job?.cancel()
        update_action_ui_state(scanStarted = false)
    }

    private fun update_scan() {
        viewModelScope.launch {
            println("Scan device")
            val scannedDevices = deviceRepository.get_detected_device()
            format_devices_date(scannedDevices)
            update_action_ui_state( null, scannedDevices, null)
            println("Scanned device: " + scannedDevices.size)
        }
    }

    fun get_device_info(device: Device) {
        deviceRepository.detailledDeviceId = device.id
    }

    fun register_screen(device: Device){
        val oldUiState = this.uiState
        if (oldUiState is ListUiState.ListState){
            uiState = ListUiState.RegisterDevice(oldUiState, device)
        }else{
            uiState = ListUiState.RegisterDevice(null, device)
        }
    }

    fun dismiss_register(){
        val oldUiState = uiState
        if (oldUiState is ListUiState.RegisterDevice && oldUiState.listState != null){
            uiState = oldUiState.listState
        }else{
            load_connected_device()
        }
        registerDeviceName = ""
    }

    fun update_register_name(name: String){
        this.registerDeviceName = name
    }

    fun register_device(device: Device){
        viewModelScope.launch {
            deviceRepository.add_new_device(device, name = registerDeviceName)
            update_connected_device()
            registerDeviceName = ""
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
        format_devices_date(connectedDevices)
        println("Device connected: " + connectedDevices.size)
        update_action_ui_state(connectedDevices = connectedDevices)
    }

    private fun format_devices_date(devices: List<Device>) {
        for (device in devices){
            val ra = device.registeredAt
            if (ra != null) {
                device.registeredAt = dateUtils.FormatDate(ra)
            }
            val da = device.discoveredAt
            if (da != null) {
                device.registeredAt = dateUtils.FormatDate(da)
            }
        }
    }
    private fun update_action_ui_state(connectedDevices: List<Device>? = null,
                                       scannedDevices: List<Device>? = null,
                                       scanStarted: Boolean? = null
    ){
        if (uiState is ListUiState.ListState) {
            // If uistate il already action state
            val oldUiState = uiState as ListUiState.ListState
            uiState = ListUiState.ListState(
                connectedDevices ?: oldUiState.connectedDevices,
                scannedDevices ?: oldUiState.scannedDevices,
                scanStarted ?: oldUiState.scanStarted
            )
        } else {
            // Default action state
            uiState = ListUiState.ListState(
                connectedDevices ?: listOf(),
                scannedDevices ?: listOf(),
                scanStarted ?: false
            )
        }
    }

}