package fr.insacvl.home2sec


import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import fr.insacvl.home2sec.data.APIRepository.ApiRepository
import fr.insacvl.home2sec.data.DeviceRepository
import fr.insacvl.home2sec.ui.HomeNavigation
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App(configuration: Configuration) {
    MaterialTheme {

        // Change here the data location
        val deviceRepository: DeviceRepository = ApiRepository(httpClient = configuration.httpClient)
        //val deviceRepository: DeviceRepository = SampleRepository()
        HomeNavigation(deviceRepository, configuration.dateUtils)
    }
}