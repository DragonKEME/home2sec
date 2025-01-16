package fr.insacvl.home2sec.ui.listScreen

import fr.insacvl.home2sec.models.Device

sealed interface ListUiState {
    data class ListState(
        val voletState: VoleyState,
        val connectedDevices: List<Device>,
        val scannedDevices: List<Device>,
        val scanStarted: Boolean
    ): ListUiState

    data class DeviceInfo(
        val device: Device,
        val listState: ListState? = null,
    ): ListUiState

    data object LoadingState: ListUiState
}