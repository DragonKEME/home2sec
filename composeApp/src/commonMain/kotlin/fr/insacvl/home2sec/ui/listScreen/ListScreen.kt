package fr.insacvl.home2sec.ui.listScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import fr.insacvl.home2sec.data.DeviceRepository
import fr.insacvl.home2sec.models.Device
import fr.insacvl.home2sec.ui.Page

@Composable
fun HomeStateCheck(navHostController: NavHostController, deviceRepository: DeviceRepository, modifier: Modifier = Modifier){
    val listViewModel: ListViewModel = viewModel { ListViewModel(deviceRepository) }
    Surface(modifier = modifier.fillMaxSize()) {
        when (val listUiState = listViewModel.uiState) {
            is ListUiState.ListState -> HomeScreen(navHostController, listViewModel, listUiState, modifier = Modifier)
            is ListUiState.LoadingState -> { }
        }

    }

}

@Composable
fun HomeScreen(
    navHostController: NavHostController,
    listViewModel: ListViewModel,
    listUiState: ListUiState.ListState,
    modifier: Modifier = Modifier
) {
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
            DeviceList(navHostController, listViewModel, device, modifier = Modifier.fillMaxWidth())
        }

        Row (horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()) {
            if (!listUiState.scanStarted) {
                Button(onClick = { listViewModel.start_scan() }) { Text("Scan device") }
            } else {
                Button(onClick = { listViewModel.stop_scan() }) { Text("Stop Scan") }
            }
        }
        if (listUiState.scanStarted){
            Row (
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly ,
                modifier = Modifier.fillMaxWidth()
            ){
                CircularProgressIndicator()
            }
        }else{
            for (device in listUiState.scannedDevices) {
                DeviceList(navHostController, listViewModel, device, modifier = Modifier.fillMaxWidth())
            }
        }
    }
}

@Composable
fun DeviceList(
    navHostController: NavHostController,
    listViewModel: ListViewModel,
    device: Device, modifier:
    Modifier = Modifier
){
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
            Button(
                onClick = {listViewModel.get_device_info(device); navHostController.navigate(Page.DeviceDetail.route)}
            ) {
                Text("Info")
            }

            Button(
                onClick = {listViewModel.delete_connected_device(device)}
            ) {
                Text("Delete")
            }
        } else {
            Button(onClick = {listViewModel.register_device(device)}) { Text("Register") }
            Button(onClick = {listViewModel.delete_detected_device(device) }) { Text("Delete") }
        }
    }
}