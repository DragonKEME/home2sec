package fr.insacvl.home2sec


import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import fr.insacvl.home2sec.data.APIRepository.ApiRepository
import fr.insacvl.home2sec.data.sampleRepository.SampleRepository
import fr.insacvl.home2sec.ui.HomeStateCheck
import fr.insacvl.home2sec.ui.HomeViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview
import io.ktor.client.HttpClient

@Composable
@Preview
fun App(clientHttp: HttpClient) {
    MaterialTheme {
        /*var showContent by remember { mutableStateOf(false) }
        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Button(onClick = { showContent = !showContent }) {
                Text("Click me!")
            }
            AnimatedVisibility(showContent) {
                //val greeting = remember { Greeting().greet() }
                Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                    Image(painterResource(Res.drawable.compose_multiplatform), null)
                    Text("Compose: oui")
                }
            }
        }*/
        val homeViewModel: HomeViewModel = viewModel { HomeViewModel(ApiRepository(clientHttp)) }
        HomeStateCheck(homeViewModel, modifier = Modifier)
    }
}