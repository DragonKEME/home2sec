package fr.insacvl.home2sec.ui.screen.list

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import fr.insacvl.home2sec.data.DeviceRepository
import fr.insacvl.home2sec.models.Device
import fr.insacvl.home2sec.ui.Component.Popup
import fr.insacvl.home2sec.ui.Page
import fr.insacvl.home2sec.utils.DateUtils

@Composable
fun HomeStateCheck(navHostController: NavHostController, deviceRepository: DeviceRepository, dateUtils: DateUtils, modifier: Modifier = Modifier){
    val listViewModel: ListViewModel = viewModel { ListViewModel(deviceRepository, dateUtils) }
    Surface(modifier = modifier.fillMaxSize()) {
        when (val listUiState = listViewModel.uiState) {
            is ListUiState.ListState -> HomeScreen(navHostController, listViewModel, listUiState, modifier = Modifier)
            is ListUiState.RegisterDevice -> PopupRegister(navHostController, listViewModel, listUiState, modifier= Modifier)
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
    Column (modifier = modifier.fillMaxWidth().verticalScroll(rememberScrollState())){
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
    Box (modifier = modifier.padding(10.dp)) {
        val borderColor = if (device.isAlive) {
            MaterialTheme.colorScheme.tertiary
        } else {
            MaterialTheme.colorScheme.error
        }
        Card(
            modifier = Modifier
                .border(2.dp, borderColor, RoundedCornerShape(3.dp)),
            colors = CardDefaults.cardColors()
                .copy(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                Box(
                    modifier = Modifier
                        .padding(start = 2.dp, end = 10.dp),
                    contentAlignment = Alignment.CenterStart,
                ) {
                    // Fix mutable variable
                    val deviceName = device.name
                    if (deviceName != null) {
                        Column {
                            Text(deviceName)
                            Text(
                                device.type,
                                fontWeight = FontWeight.Thin,
                                fontStyle = FontStyle.Italic
                            )
                        }
                    } else {
                        Text(
                            device.type,
                            fontWeight = FontWeight.Thin,
                            fontStyle = FontStyle.Italic,
                        )
                    }
                }

                Row (
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Device registered
                    if (device.registered) {
                        Box(
                            contentAlignment = Alignment.Center,
                        ) {
                            Button(
                                onClick = {
                                    listViewModel.get_device_info(device); navHostController.navigate(
                                    Page.DeviceDetail.route
                                )
                                },
                            ) {
                                Text("Info")
                            }
                        }
                        Spacer(Modifier.width(10.dp))
                        Box(
                            contentAlignment = Alignment.Center,
                        ) {
                            Button(
                                onClick = { listViewModel.delete_connected_device(device) },
                            ) {
                                Text("Delete")
                            }
                        }
                    } else {
                        Button(
                            onClick = { listViewModel.register_screen(device) },
                        ) { Text("Register") }
                        Spacer(Modifier.width(10.dp))
                        Button(
                            onClick = { listViewModel.delete_detected_device(device) },
                        ) { Text("Delete") }
                    }
                }
            }
        }
    }
}

@Composable
fun PopupRegister(
    navHostController: NavHostController,
    listViewModel: ListViewModel,
    listUiState: ListUiState.RegisterDevice,
    modifier: Modifier = Modifier
){
    val device = listUiState.device
    Popup(
        modifier = modifier,
        onDismiss = { listViewModel.dismiss_register() },
        background = if (listUiState.listState != null){
            { HomeScreen(navHostController, listViewModel, listUiState.listState, modifier = modifier) }
        }else{
            null
        }
    ){
        Column(horizontalAlignment = Alignment.Start, modifier = Modifier.width(200.dp)) {
            Row {
                OutlinedTextField(
                    value = listViewModel.registerDeviceName,
                    singleLine = true,
                    onValueChange = { listViewModel.update_register_name(it) },
                    label = { Text("Device Name") },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done,
                        keyboardType = KeyboardType.Text
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = { listViewModel.register_device(device) }
                    ),
                    modifier = Modifier.fillMaxWidth().padding(bottom = 10.dp)
                )
            }
            Button(
                onClick = { listViewModel.register_device(device) },
                modifier = Modifier.align(Alignment.End).fillMaxWidth()
            ) {
                Text("Register")
            }

        }
    }
}