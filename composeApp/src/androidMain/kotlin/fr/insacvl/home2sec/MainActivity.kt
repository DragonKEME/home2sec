package fr.insacvl.home2sec

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val httpClient = HttpClient(OkHttp) {
            install(ContentNegotiation) {
                json()
            }
        }
        setContent {
            App(httpClient)
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    val httpClient = HttpClient(OkHttp)
    App(httpClient)
}