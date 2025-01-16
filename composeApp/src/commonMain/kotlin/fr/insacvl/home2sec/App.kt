package fr.insacvl.home2sec


import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import fr.insacvl.home2sec.data.sampleRepository.SampleRepository
import fr.insacvl.home2sec.ui.HomeScreen
import fr.insacvl.home2sec.ui.HomeStateCheck
import fr.insacvl.home2sec.ui.HomeUiState
import fr.insacvl.home2sec.ui.HomeViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview

import home2sec.composeapp.generated.resources.Res
import home2sec.composeapp.generated.resources.compose_multiplatform

@Composable
@Preview
fun App() {
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

        HomeStateCheck(modifier = Modifier)
    }
}