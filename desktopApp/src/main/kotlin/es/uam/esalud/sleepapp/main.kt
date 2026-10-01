package es.uam.esalud.sleepapp

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import es.uam.esalud.sleepapp.audio.GrabadorSimulado
import es.uam.esalud.sleepapp.datos.RepositorioEnMemoria

/**
 * Punto de entrada de la versión de escritorio.
 *
 * Usa el grabador simulado: no hay micrófono ni permisos de por medio, lo que
 * permite desarrollar la interfaz sin depender del emulador.
 */
fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Registro de sueño"
    ) {
        App(
            grabador = GrabadorSimulado(),
            repositorio = RepositorioEnMemoria()
        )
    }
}
