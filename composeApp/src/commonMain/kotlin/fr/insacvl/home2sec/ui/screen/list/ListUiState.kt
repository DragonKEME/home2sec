package fr.insacvl.home2sec.ui.screen.list

import fr.insacvl.home2sec.models.Device

sealed interface ListUiState {
    data class ListState(
        val connectedDevices: List<Device>,
        val scannedDevices: List<Device>,
        val scanStarted: Boolean
    ): ListUiState

    data class RegisterDevice(
        val listState: ListState?,
        val device: Device
    ): ListUiState

    data object LoadingState: ListUiState
}