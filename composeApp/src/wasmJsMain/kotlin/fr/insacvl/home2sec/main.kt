package fr.insacvl.home2sec

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import fr.insacvl.home2sec.implementation.DateUtilsJs
import fr.insacvl.home2sec.utils.DateUtils
import io.ktor.client.HttpClient
import io.ktor.client.engine.js.Js
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.browser.document

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    ComposeViewport(document.body!!) {
        val httpClient = HttpClient(Js) {
            install(ContentNegotiation) {
                json()
            }
        }
        val dateUtils: DateUtils = DateUtilsJs()
        val configuration = Configuration(httpClient, dateUtils)
        App(configuration)
    }
}