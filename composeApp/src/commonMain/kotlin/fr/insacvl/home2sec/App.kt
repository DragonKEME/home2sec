package fr.insacvl.home2sec


import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import fr.insacvl.home2sec.data.APIRepository.ApiRepository
import fr.insacvl.home2sec.data.DeviceRepository
import fr.insacvl.home2sec.data.sampleRepository.SampleRepository
import fr.insacvl.home2sec.ui.HomeNavigation
import io.ktor.client.HttpClient
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App(clientHttp: HttpClient) {
    MaterialTheme {

        // Change here the data location
        val deviceRepository: DeviceRepository = ApiRepository(httpClient = clientHttp)
        //val deviceRepository: DeviceRepository = SampleRepository()
        HomeNavigation(deviceRepository)
    }
}