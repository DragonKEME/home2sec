package fr.insacvl.home2sec

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Home 2SEC",
    ) {
        App()
    }
}