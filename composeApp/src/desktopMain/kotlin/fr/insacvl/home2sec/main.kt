package fr.insacvl.home2sec

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import io.ktor.client.HttpClient
import io.ktor.client.engine.java.Java
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Home 2SEC",
    ) {
        val httpClient = HttpClient(Java) {
            install(ContentNegotiation) {
                json()
            }
        }
        App(httpClient)
    }
}