package es.uam.esalud.sleepapp

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "SleepApp",
    ) {
        App()
    }
}