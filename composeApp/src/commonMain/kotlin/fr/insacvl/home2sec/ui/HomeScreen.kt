package fr.insacvl.home2sec.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.window.Popup
import fr.insacvl.home2sec.models.Device

@Composable
fun HomeStateCheck(homeViewModel: HomeViewModel, modifier: Modifier = Modifier){
    Surface(modifier = modifier.fillMaxSize()) {
        when (val homeUiState = homeViewModel.uiState) {
            is HomeUiState.ActionState -> HomeScreen(homeViewModel, homeUiState, modifier = Modifier)
            is HomeUiState.DeviceInfo -> PopupInfo(homeViewModel, device = homeUiState.device)
            is HomeUiState.LoadingState -> { }
        }

    }

}

@Composable
fun HomeScreen(homeViewModel: HomeViewModel, homeUiState: HomeUiState.ActionState, modifier: Modifier = Modifier) {
    println("redraw home screen")
    Column (modifier = modifier.fillMaxWidth()){
        if (homeUiState.voletState == VoleyState.UP){
            Text("Volet Montant")
        }else if (homeUiState.voletState == VoleyState.DOWN){
            Text("Volet Descendant")
        }else {
            Text("Volet pas bougé")
        }

        Row (horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()){
            Button(
                onClick = {
                    homeViewModel.update_volet(VoleyState.UP)
                }
            ) {
                Text("Monter Volet")
            }
            Button(
                onClick = {
                    homeViewModel.update_volet(VoleyState.NOP)
                }
            ) {
                Text("Stop")
            }
            Button(
                onClick = {
                    homeViewModel.update_volet(VoleyState.DOWN)
                }
            ) {
                Text("Baisser Volet")
            }
        }
        for (device in homeUiState.connectedDevices) {
            println("draw device: " + device.id)
            DeviceList(homeViewModel, device, modifier = Modifier.fillMaxWidth())
        }

        Row (horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()) {
            if (!homeUiState.scanStarted) {
                Button(onClick = { homeViewModel.start_scan() }) { Text("Start Scan") }
            } else {
                Button(onClick = { homeViewModel.stop_scan() }) { Text("Stop Scan") }
            }
        }
        for (device in homeUiState.scannedDevices) {
            DeviceList(homeViewModel, device, modifier = Modifier.fillMaxWidth())
        }
    }
}

@Composable
fun DeviceList(homeViewModel: HomeViewModel, device: Device, modifier: Modifier = Modifier){
    Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = modifier) {
        // Fix mutable variable
        val deviceName = device.name
        if (deviceName != null) {
            Column {
                Text(deviceName)
                Text(device.type, fontWeight = FontWeight.Thin, fontStyle = FontStyle.Italic)
            }
        }else {
            Text(device.type, fontWeight = FontWeight.Thin, fontStyle = FontStyle.Italic)
        }
        Text("" + device.id)

        // Device registered
        if (device.registered) {
            Text(device.registeredAt ?: "No date")
            Button(onClick = {homeViewModel.get_device_info(device)}) { Text("Info") }
            Button(onClick = {homeViewModel.delete_connected_device(device) }) { Text("Delete") }
        } else {
            Button(onClick = {homeViewModel.register_device(device)}) { Text("Register") }
            Button(onClick = {homeViewModel.delete_detected_device(device) }) { Text("Delete") }
        }
    }
}


@Composable
fun PopupInfo(homeViewModel: HomeViewModel, device: Device){
    Popup(
        alignment = Alignment.Center,

    ) {
        Column (horizontalAlignment = Alignment.Start) {
            Row {
                Text("Device Type: ", fontWeight = FontWeight.Bold)
                Text(device.type)
            }
            Row {
                Text("Id: ", fontWeight = FontWeight.Bold)
                Text(device.id.toString())
            }
            Row {
                Text("Device Name: ", fontWeight = FontWeight.Bold)
                Text(device.name ?: "No name")
            }
            Row {
                Text("Registered at: ", fontWeight = FontWeight.Bold)
                Text(device.registeredAt ?: "No date")
            }
            Row {
                Text("Last seen: ", fontWeight = FontWeight.Bold)
                Text(device.lastSeen ?: "Never seen")
            }
            Button(
                onClick = { homeViewModel.dismiss_device_info() },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text("OK")
            }

        }
    }
}