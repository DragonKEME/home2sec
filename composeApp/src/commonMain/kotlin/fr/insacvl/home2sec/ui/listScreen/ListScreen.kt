package fr.insacvl.home2sec.ui.listScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import fr.insacvl.home2sec.models.Device

@Composable
fun HomeStateCheck(listViewModel: ListViewModel, modifier: Modifier = Modifier){
    Surface(modifier = modifier.fillMaxSize()) {
        when (val listUiState = listViewModel.uiState) {
            is ListUiState.ListState -> HomeScreen(listViewModel, listUiState, modifier = Modifier)
            is ListUiState.DeviceInfo -> { PopupInfo(listViewModel, listUiState, modifier = Modifier) }
            is ListUiState.LoadingState -> { }
        }

    }

}

@Composable
fun HomeScreen(listViewModel: ListViewModel, listUiState: ListUiState.ListState, modifier: Modifier = Modifier) {
    println("redraw home screen")
    Column (modifier = modifier.fillMaxWidth()){
        if (listUiState.voletState == VoleyState.UP){
            Text("Volet Montant")
        }else if (listUiState.voletState == VoleyState.DOWN){
            Text("Volet Descendant")
        }else {
            Text("Volet pas bougé")
        }

        Row (horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()){
            Button(
                onClick = {
                    listViewModel.update_volet(VoleyState.UP)
                }
            ) {
                Text("Monter Volet")
            }
            Button(
                onClick = {
                    listViewModel.update_volet(VoleyState.NOP)
                }
            ) {
                Text("Stop")
            }
            Button(
                onClick = {
                    listViewModel.update_volet(VoleyState.DOWN)
                }
            ) {
                Text("Baisser Volet")
            }
        }
        for (device in listUiState.connectedDevices) {
            println("draw device: " + device.id)
            DeviceList(listViewModel, device, modifier = Modifier.fillMaxWidth())
        }

        Row (horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()) {
            if (!listUiState.scanStarted) {
                Button(onClick = { listViewModel.start_scan() }) { Text("Start Scan") }
            } else {
                Button(onClick = { listViewModel.stop_scan() }) { Text("Stop Scan") }
            }
        }
        for (device in listUiState.scannedDevices) {
            DeviceList(listViewModel, device, modifier = Modifier.fillMaxWidth())
        }
    }
}

@Composable
fun DeviceList(listViewModel: ListViewModel, device: Device, modifier: Modifier = Modifier){
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
            Button(onClick = {listViewModel.get_device_info(device)}) { Text("Info") }
            Button(onClick = {listViewModel.delete_connected_device(device) }) { Text("Delete") }
        } else {
            Button(onClick = {listViewModel.register_device(device)}) { Text("Register") }
            Button(onClick = {listViewModel.delete_detected_device(device) }) { Text("Delete") }
        }
    }
}


@Composable
fun PopupInfo(listViewModel: ListViewModel, listUiState: ListUiState.DeviceInfo, modifier: Modifier = Modifier){

    val device = listUiState.device
    if (listUiState.listState != null){
        HomeScreen(listViewModel, listUiState.listState, modifier = modifier)
    }
    Surface (
        color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.2f),
        modifier = modifier.fillMaxSize()
    ){
        Popup(
            alignment = Alignment.Center,
        ) {
            Box (modifier = Modifier.clip(RoundedCornerShape(10.dp))) {
                Box(
                    Modifier
                        .background(MaterialTheme.colorScheme.background)
                        .padding(20.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.Start) {
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
                        Row {
                            Text("IP: ", fontWeight = FontWeight.Bold)
                            Text(device.ip)
                        }
                        Button(
                            onClick = { listViewModel.dismiss_device_info() },
                            modifier = Modifier.align(Alignment.End)
                        ) {
                            Text("OK")
                        }

                    }
                }
            }

        }
    }
}