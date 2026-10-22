package es.uam.esalud.sleepapp

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import es.uam.esalud.sleepapp.audio.GrabadorSimulado
import es.uam.esalud.sleepapp.datos.RepositorioEnMemoria
import kotlinx.browser.document

/**
 * Punto de entrada de la versión web.
 *
 * Es la versión que se despliega para que cualquiera pueda abrirla desde su
 * móvil, sea Android o iPhone. Usa el grabador simulado, porque en el
 * navegador no hay MediaRecorder de Android.
 */
@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    ComposeViewport(document.body!!) {
        App(
            grabador = GrabadorSimulado(),
            repositorio = RepositorioEnMemoria()
        )
    }
}
