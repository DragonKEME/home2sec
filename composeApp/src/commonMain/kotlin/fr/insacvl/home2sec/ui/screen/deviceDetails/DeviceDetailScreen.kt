package fr.insacvl.home2sec.ui.screen.deviceDetails

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import fr.insacvl.home2sec.data.DeviceRepository
import fr.insacvl.home2sec.models.Device
import fr.insacvl.home2sec.models.DeviceAction
import fr.insacvl.home2sec.models.DeviceLog
import fr.insacvl.home2sec.models.SensorData
import fr.insacvl.home2sec.ui.Component.Popup
import fr.insacvl.home2sec.utils.DateUtils

@Composable
fun DeviceDetailScreen(navHostController: NavHostController,deviceRepository: DeviceRepository, dateUtils: DateUtils, modifier: Modifier = Modifier){
    val deviceDetailViewModel: DeviceDetailViewModel = viewModel { DeviceDetailViewModel(deviceRepository, dateUtils) }
    Surface (modifier = modifier.fillMaxSize()) {
        when (val uiState = deviceDetailViewModel.uiState) {
            is DeviceDetailUiState.DeviceInfo -> DeviceDetails(
                navHostController,
                deviceDetailViewModel,
                uiState,
                modifier
            )

            is DeviceDetailUiState.EditDeviceName -> PopupDeviceEdit(
                navHostController,
                deviceDetailViewModel,
                uiState,
                modifier
            )
        }
    }
}

@Composable
fun DeviceDetails(
    navHostController: NavHostController,
    deviceDetailViewModel: DeviceDetailViewModel,
    deviceDetailUiState: DeviceDetailUiState.DeviceInfo,
    modifier: Modifier = Modifier
) {
    Surface (
        modifier = modifier.fillMaxSize()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth().verticalScroll(rememberScrollState())
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
                Button(onClick = {navHostController.popBackStack()}) {
                    Text("Back")
                }
            }
            if (deviceDetailUiState.device == null) {
                Text("no device")
            } else {
                DeviceDetail(deviceDetailUiState.device, editButton = {
                    deviceDetailViewModel.change_name_screen()
                })
                for (action in deviceDetailUiState.action) {
                    ActionElement(deviceDetailViewModel, action, deviceDetailUiState.device)
                }
                for (sensor in deviceDetailUiState.sensor) {
                    SensorElement(sensor)
                }
                if (deviceDetailUiState.logs.isNotEmpty()) {
                    Box(modifier = modifier.clip(RoundedCornerShape(10.dp))) {
                        Box(
                            Modifier
                                .background(MaterialTheme.colorScheme.background)
                                .padding(30.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Column {
                                for (log in deviceDetailUiState.logs) {
                                    LogElement(log)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DeviceDetail(device: Device, editButton: () -> Unit, modifier: Modifier = Modifier){
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .border(2.dp, MaterialTheme.colorScheme.tertiary, RoundedCornerShape(11.dp)),
    ) {
        Box(
            Modifier
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .padding(30.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.Start) {
                Row {
                    Text("Device Name: ", fontWeight = FontWeight.Bold)
                    Text(device.name ?: "No name")
                }
                Row {
                    Text("Id: ", fontWeight = FontWeight.Bold)
                    Text(device.id.toString())
                }
                Row {
                    Text("Device Type: ", fontWeight = FontWeight.Bold)
                    Text(device.type)
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
                Row {
                    Button(
                        onClick = editButton
                    ){
                        Text("Change Name")
                    }
                }
            }
        }
    }
}

@Composable
fun ActionElement(
    deviceDetailViewModel: DeviceDetailViewModel,
    action: DeviceAction,
    device: Device,
    modifier: Modifier = Modifier
){
    Box(modifier = modifier.clip(RoundedCornerShape(10.dp))) {
        Box(
            Modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(30.dp),
            contentAlignment = Alignment.Center
        ) {
            Row (horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text(action.actionName)

                if ( action.actionRunning ){
                    CircularProgressIndicator()
                }else{
                    Button(
                        onClick = {deviceDetailViewModel.do_action(device, action)}
                    ) {
                        Text(action.actionName)
                    }
                }
            }
        }
    }

}

@Composable
fun SensorElement(sensorData: SensorData, modifier: Modifier = Modifier){
    Box(modifier = modifier.clip(RoundedCornerShape(10.dp))) {
        Box(
            Modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(30.dp),
            contentAlignment = Alignment.Center
        ) {
            Row(horizontalArrangement = Arrangement.SpaceBetween) {
                Text(sensorData.sensorType)
                Text("         ")
                Text(sensorData.value.toString(), fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun LogElement(log: DeviceLog){
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(log.logType)
        Text(log.message)
        Text(log.timestamp)
    }
}

@Composable
fun PopupDeviceEdit(
    navHostController: NavHostController,
    detailViewModel: DeviceDetailViewModel,
    editUiState: DeviceDetailUiState.EditDeviceName,
    modifier: Modifier
){
    Popup(
        modifier = modifier,
        onDismiss = { detailViewModel.dismiss_update() },
        background = {
            DeviceDetails(
                navHostController,
                detailViewModel,
                editUiState.deviceInfo,
                modifier = modifier
            )
        }
    ){
        Column(horizontalAlignment = Alignment.Start, modifier = Modifier.width(200.dp)) {
            Row {
                OutlinedTextField(
                    value = detailViewModel.editDeviceName,
                    singleLine = true,
                    onValueChange = { detailViewModel.update_name_string(it) },
                    label = { Text("Device Name") },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done,
                        keyboardType = KeyboardType.Text
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = { detailViewModel.update_device() }
                    ),
                    modifier = Modifier.fillMaxWidth().padding(bottom = 10.dp)
                )
            }
            Button(
                onClick = { detailViewModel.update_device() },
                modifier = Modifier.align(Alignment.End).fillMaxWidth()
            ) {
                Text("Update Device")
            }

        }
    }
}