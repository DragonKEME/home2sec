package fr.insacvl.home2sec.ui

import fr.insacvl.home2sec.models.Device

sealed interface HomeUiState {
    data class ActionState(
        val voletState: VoleyState,
        val connectedDevices: List<Device>,
        val scannedDevices: List<Device>,
        val scanStarted: Boolean
    ): HomeUiState

    data class DeviceInfo(
        val device: Device
    ): HomeUiState

    data object LoadingState: HomeUiState
}